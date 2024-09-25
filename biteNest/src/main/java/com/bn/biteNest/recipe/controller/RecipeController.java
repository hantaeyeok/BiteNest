package com.bn.biteNest.recipe.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.common.FileUpload;
import com.bn.biteNest.common.Message;
import com.bn.biteNest.recipe.model.service.IngredientService;
import com.bn.biteNest.recipe.model.service.IngredientTypeService;
import com.bn.biteNest.recipe.model.service.RecipeCategory1Service;
import com.bn.biteNest.recipe.model.service.RecipeCategory2Service;
import com.bn.biteNest.recipe.model.service.RecipeService;
import com.bn.biteNest.recipe.model.service.RecipeStepService;
import com.bn.biteNest.recipe.model.service.TipService;
import com.bn.biteNest.recipe.model.vo.IngredientTypeVO;
import com.bn.biteNest.recipe.model.vo.IngredientVO;
import com.bn.biteNest.recipe.model.vo.RecipeStepVO;
import com.bn.biteNest.recipe.model.vo.RecipeVO;
import com.bn.biteNest.recipe.model.vo.TipVO;

import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/recipes")
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;

	@Autowired
	private IngredientTypeService ingredientTypeService;

	@Autowired
	private RecipeStepService recipeStepService;

	@Autowired
	private TipService tipService;

	@Autowired
	private IngredientService ingredientService;
		
	@Autowired
	private FileUpload fileUpload;
	
	@Autowired
	private RecipeCategory1Service recipeCategory1Service;
	
	@Autowired
	private RecipeCategory2Service recipeCategory2Service;
	
	
	@PostMapping("/register")
	public ResponseEntity<Message> registerRecipe(
	        @RequestPart Map<String, String> formData,  // 일반 데이터를 Map으로 받음
	        @RequestPart("ingredients") List<Map<String, String>> ingredients,  // 재료 정보 배열
	        @RequestPart("steps") List<Map<String, String>> steps,  // 조리 단계 설명 배열
	        @RequestPart("tips") List<Map<String, String>> tips,  // 팁 배열
	        @RequestPart("imageFiles") Map<String, List<MultipartFile>> imageFiles,  // 이미지 파일들
	        HttpSession session) {
	
	    try {
	        RecipeVO recipeVO = createRecipe(formData, session, imageFiles);// 레시피 생성 및 저장
	        saveIngredients(recipeVO.getRecipeCD(),ingredients);// 재료 저장
	        saveSteps(recipeVO.getRecipeCD(), steps, imageFiles.get("stepImages"), session, recipeVO.getRecipeNM());// 조리 단계 저장
	        saveTips(recipeVO.getRecipeCD(), tips);// 팁 저장
	
	        return ResponseEntity.status(HttpStatus.OK).body(Message.builder().data("").message("insert recipe").build());
	
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Message.builder().data("").message("insert recipe").build());
	    }
	}
	
	// 1. 레시피 정보 생성
	private RecipeVO createRecipe(Map<String, String> formData, HttpSession session, Map<String, List<MultipartFile>> imageFiles) throws Exception {
	    String recipeName = formData.get("recipeName");
		String recipeDescription = formData.get("recipeDescription");
		int estimatedTime = Integer.parseInt(formData.get("estimatedTime"));
		int cookingServings = Integer.parseInt(formData.get("cookingServings"));
		String category1Name = formData.get("category1Name");
		String category2Name = formData.get("category2Name");
		
		// 카테고리 조회
		int category1CD = recipeCategory1Service.getCategory1CDByName(category1Name);
		int category2CD = recipeCategory2Service.getCategory2CDByName(category2Name);
		
		RecipeVO recipeVO = new RecipeVO();
		recipeVO.setRecipeNM(recipeName);
		recipeVO.setRecipeDescription(recipeDescription);
		recipeVO.setEstimatedTime(estimatedTime);
		recipeVO.setCookingServings(cookingServings);
		recipeVO.setCategory1CD(category1CD);
		recipeVO.setCategory2CD(category2CD);
		
		if (imageFiles.containsKey("mainImage")) {// 메인 이미지 저장
		String mainImagePath = fileUpload.saveImage(imageFiles.get("mainImage").get(0), session, recipeName, null);
		    recipeVO.setRecipeMainImage(mainImagePath);
		}
		
	    recipeService.insertRecipe(recipeVO);
	    return recipeVO;
	}

	// 2. 재료 정보 저장
	private void saveIngredients(int recipeCD, List<Map<String, String>> ingredients) {
	    for (Map<String, String> ingredient : ingredients) {
	        String ingredientName = ingredient.get("ingredientName");
	        String ingredientAmount = ingredient.get("ingredientAmount");
	        String ingredientType = ingredient.get("ingredientType");

	        // 1번 : 같은 이름의 재료가 있는지 확인
	        IngredientVO existingIngredient = ingredientService.findByName(ingredientName);
	        int ingredientCD;

	        if (existingIngredient != null) {
	            // 1-1. 재료가 존재하면 재료의 기본키를 사용
	            ingredientCD = existingIngredient.getIngredientCD();
	        } else {
	            // 2번 : 재료가 없으면 새로운 재료 등록
	            IngredientVO newIngredient = new IngredientVO();
	            newIngredient.setIngredientNM(ingredientName);
	            ingredientService.insertIngredient(newIngredient);

	            // 새로 등록된 재료의 기본키 가져오기 - MyBatis selectKey를 사용
	            ingredientCD = newIngredient.getIngredientCD();
	        }

	        // 재료 유형과 양을 IngredientTypeVO로 저장
	        IngredientTypeVO ingredientTypeVO = new IngredientTypeVO();
	        ingredientTypeVO.setRecipeCD(recipeCD);  // 레시피 ID
	        ingredientTypeVO.setIngredientCD(ingredientCD);  // 재료 기본키
	        ingredientTypeVO.setIngredientAmt(ingredientAmount);  // 재료 양
	        ingredientTypeVO.setIngredientType(ingredientType);  // 재료 유형

	        // 재료 유형과 양을 DB에 저장
	        ingredientTypeService.insertIngredientType(ingredientTypeVO);
	    }
	}

	// 3. 조리 단계 정보 저장
	private void saveSteps(int recipeCD, List<Map<String, String>> steps, List<MultipartFile> stepImages, HttpSession session, String recipeName) throws Exception {
	    for (int i = 0; i < steps.size(); i++) {
	        Map<String, String> step = steps.get(i);
	        RecipeStepVO stepVO = new RecipeStepVO();
	        stepVO.setRecipeCD(recipeCD);
	        stepVO.setStepORD(Integer.parseInt(step.get("stepsOrder")));
	        stepVO.setInstruction(step.get("stepsDescription"));

	        // 조리 단계 이미지 저장
	        if (stepImages != null && i < stepImages.size()) {  // 이미지가 있는지 확인
	            MultipartFile stepImage = stepImages.get(i);
	            if (!stepImage.isEmpty()) {
	                String stepImagePath = fileUpload.saveImage(stepImage, session, recipeName, i + 1);
	                stepVO.setImageURL(stepImagePath);
	            }
	        }

	        recipeStepService.insertRecipeStep(stepVO);
	    }
	}

    // 4. 팁 정보 저장
    private void saveTips(int recipeCD, List<Map<String, String>> tips) {
        for (Map<String, String> tip : tips) {
            TipVO tipVO = new TipVO();
            tipVO.setRecipeCD(recipeCD);
            tipVO.setTipContent(tip.get("tipContent"));
            tipVO.setTipORD(Integer.parseInt(tip.get("tipOrder")));
            tipService.insertTip(tipVO);
        }
    }
}
//@PostMapping("/register")
//public ResponseEntity<Message> registerRecipe(
//        @RequestPart Map<String, String> formData,  // 일반 데이터를 Map으로 받음
//        @RequestPart("imageFiles") List<MultipartFile> imageFiles,  // 이미지 파일들
//        HttpSession session) {
//
//	try {
//        RecipeVO recipeVO = new RecipeVO();
//        recipeVO.setRecipeNM(recipeName);
//        recipeVO.setRecipeDescription(recipeDescription);
//        recipeVO.setEstimatedTime(estimatedTime);
//        recipeVO.setCookingServings(cookingServings);
//        recipeVO.setCategory1CD(category1CD);
//        recipeVO.setCategory2CD(category2CD);
//
//        // 첫 번째 이미지를 레시피 메인 이미지로 저장
//        if (!imageFiles.isEmpty()) {
//            String mainImagePath = fileUpload.saveImage(imageFiles.get(0), session, recipeName, null);  // 메인 이미지
//            recipeVO.setRecipeMainImage(mainImagePath);
//        }
//
//        // 레시피 저장
//        recipeService.insertRecipe(recipeVO);
//
//        // 재료 저장
//        
//        //1번 : 같은 이름의 재료가 있나 확인 있으면 재료 타입 테이블에만 추가 데이터 중복 저장 받ㅇ지
//        //2버 : 같은 이름의 재료가 없으면 재료를 등록 재료 기본키받아서 그 재료의 기본키와 재료타입 테이블에 저장
//        for (int i = 0; i < ingredientNames.size(); i++) {
//            String ingredientName = ingredientNames.get(i);
//
//            // 1번 : 같은 이름의 재료가 있는지 확인
//            IngredientVO existingIngredient = ingredientService.findByName(ingredientName);
//            int ingredientCD;
//
//            if (existingIngredient != null) {// 1-1. 재료가 존재하면 재료의 기본키를 사용
//                ingredientCD = existingIngredient.getIngredientCD();
//            } else {// 2번 : 재료가 없으면 새로운 재료 등록
//                IngredientVO newIngredient = new IngredientVO();
//                newIngredient.setIngredientNM(ingredientName);
//                ingredientService.insertIngredient(newIngredient);
//
//                // 새로 등록된 재료의 기본키 가져오기- selectKey로 등록해
//                ingredientCD = newIngredient.getIngredientCD();
//            }
//
//            // 재료 유형과 양을 IngredientTypeVO로 저장
//            IngredientTypeVO ingredientTypeVO = new IngredientTypeVO();
//            ingredientTypeVO.setRecipeCD(recipeVO.getRecipeCD());  // 레시피 ID
//            ingredientTypeVO.setIngredientCD(ingredientCD);       // 재료 기본키
//            ingredientTypeVO.setIngredientAmt(ingredientAmounts.get(i));  // 재료 양
//            ingredientTypeVO.setIngredientType(ingredientTypes.get(i));  // 재료 유형
//
//            ingredientTypeService.insertIngredientType(ingredientTypeVO);
//        }
//
//        // 조리 단계 저장
//        for (int i = 0; i < steps.size(); i++) {
//            RecipeStepVO stepVO = new RecipeStepVO();
//            stepVO.setRecipeCD(recipeVO.getRecipeCD());
//            stepVO.setStepORD(i+1); // 조리 순서
//            stepVO.setInstruction(steps.get(i));
//
//            // 조리 단계 이미지가 있는 경우 처리
//            if (i + 1 < imageFiles.size()) {
//                String stepImagePath = fileUpload.saveImage(imageFiles.get(i + 1), session, recipeName, i + 1);  // 스탭 번호 추가
//                stepVO.setImageURL(stepImagePath);
//            }
//
//            recipeStepService.insertRecipeStep(stepVO);
//        }
//
//        return successMessage;
//
//    } catch (Exception e) {
//        return failMessage;
//    }
//}
//@PostMapping("/register")
//public ResponseEntity<Message> registerRecipe(
//        @RequestPart Map<String, String> formData,  // 일반 데이터를 Map으로 받음
//        @RequestPart("imageFiles") List<MultipartFile> imageFiles,  // 이미지 파일들
//        HttpSession session) {
//
//    try {
//    	// 1. 일반 데이터 처리 (Map에서 가져옴)
//        String recipeName = formData.get("recipeName");
//        String recipeDescription = formData.get("recipeDescription");
//        int estimatedTime = Integer.parseInt(formData.get("estimatedTime"));
//        int cookingServings = Integer.parseInt(formData.get("cookingServings"));
//        
//        // 카테고리 이름으로 카테고리 ID 조회 (추가 부분)
//        String category1Name = formData.get("category1Name"); // 한글로 들어온 카테고리 1 이름
//        String category2Name = formData.get("category2Name"); // 한글로 들어온 카테고리 2 이름
//
//        // 카테고리 이름으로 ID 조회 로직 (DB에서 검색하거나 고정값일 경우 매핑)
//        int category1CD = category1Service.getCategory1CDByName(category1Name); // 카테고리 1 ID 조회
//        int category2CD = category2Service.getCategory2CDByName(category2Name); // 카테고리 2 ID 조회
//
//        List<String> ingredientNames = List.of(formData.get("ingredientNames").split(","));  // 재료 이름 리스트로 변환
//        List<String> ingredientAmounts = List.of(formData.get("ingredientAmounts").split(","));  // 재료 양 리스트로 변환
//        List<String> ingredientTypes = List.of(formData.get("ingredientTypes").split(","));  // 재료 유형 리스트로 변환
//        List<String> steps = List.of(formData.get("steps").split(","));  // 조리 단계 리스트로 변환
//        List<String> tips = List.of(formData.get("tips").split(","));  // 팁 리스트로 변환
//        
//        // 2. 레시피 VO 생성 및 데이터 설정
//        RecipeVO recipeVO = new RecipeVO();
//        recipeVO.setRecipeNM(recipeName);
//        recipeVO.setRecipeDescription(recipeDescription);
//        recipeVO.setEstimatedTime(estimatedTime);
//        recipeVO.setCookingServings(cookingServings);
//        recipeVO.setCategory1CD(category1CD);
//        recipeVO.setCategory2CD(category2CD);
//
//        // 3. 첫 번째 이미지를 레시피 메인 이미지로 저장
//        if (!imageFiles.isEmpty()) {
//            String mainImagePath = fileUpload.saveImage(imageFiles.get(0), session, recipeName, null);  // 메인 이미지
//            recipeVO.setRecipeMainImage(mainImagePath);
//        }
//
//        // 4. 레시피 저장
//        recipeService.insertRecipe(recipeVO);
//
//        // 5. 재료 저장
//        for (int i = 0; i < ingredientNames.size(); i++) {
//            IngredientTypeVO ingredientTypeVO = new IngredientTypeVO();
//            ingredientTypeVO.setRecipeCD(recipeVO.getRecipeCD());  // 레시피 ID
//            ingredientTypeVO.setIngredientAmt(ingredientAmounts.get(i)); // 재료 양
//            ingredientTypeVO.setIngredientType(ingredientTypes.get(i));  // 재료 유형
//            ingredientTypeService.insertIngredientType(ingredientTypeVO);
//        }
//
//        // 6. 조리 단계 저장
//        for (int i = 0; i < steps.size(); i++) {
//            RecipeStepVO stepVO = new RecipeStepVO();
//            stepVO.setRecipeCD(recipeVO.getRecipeCD());
//            stepVO.setStepORD(i + 1);  // 조리 순서
//            stepVO.setInstruction(steps.get(i));
//
//            // 조리 단계 이미지가 있는 경우 처리
//            if (i + 1 < imageFiles.size()) {
//                String stepImagePath = fileUpload.saveImage(imageFiles.get(i + 1), session, recipeName, i + 1);  // 스탭 번호 추가
//                stepVO.setImageURL(stepImagePath);
//            }
//
//            recipeStepService.insertRecipeStep(stepVO);
//        }
//        
//     // 7. 팁 저장
//        for (int i = 0; i < tips.size(); i++) {
//            TipVO tipVO = new TipVO();
//            tipVO.setRecipeCD(recipeVO.getRecipeCD());  // 레시피 ID
//            tipVO.setTipContent(tips.get(i));           // 팁 내용
//            tipVO.setTipORD(i + 1);                     // 팁 순서
//            tipService.insertTip(tipVO);
//        }
//
//        return successMessage;
//
//    } catch (Exception e) {
//        return failMessage;
//    }
//}
//}	
	
	
	
	
	
