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
	        @RequestPart Map<String, String> formData,  // �Ϲ� �����͸� Map���� ����
	        @RequestPart("ingredients") List<Map<String, String>> ingredients,  // ��� ���� �迭
	        @RequestPart("steps") List<Map<String, String>> steps,  // ���� �ܰ� ���� �迭
	        @RequestPart("tips") List<Map<String, String>> tips,  // �� �迭
	        @RequestPart("imageFiles") Map<String, List<MultipartFile>> imageFiles,  // �̹��� ���ϵ�
	        HttpSession session) {
	
	    try {
	        RecipeVO recipeVO = createRecipe(formData, session, imageFiles);// ������ ���� �� ����
	        saveIngredients(recipeVO.getRecipeCD(),ingredients);// ��� ����
	        saveSteps(recipeVO.getRecipeCD(), steps, imageFiles.get("stepImages"), session, recipeVO.getRecipeNM());// ���� �ܰ� ����
	        saveTips(recipeVO.getRecipeCD(), tips);// �� ����
	
	        return ResponseEntity.status(HttpStatus.OK).body(Message.builder().data("").message("insert recipe").build());
	
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Message.builder().data("").message("insert recipe").build());
	    }
	}
	
	// 1. ������ ���� ����
	private RecipeVO createRecipe(Map<String, String> formData, HttpSession session, Map<String, List<MultipartFile>> imageFiles) throws Exception {
	    String recipeName = formData.get("recipeName");
		String recipeDescription = formData.get("recipeDescription");
		int estimatedTime = Integer.parseInt(formData.get("estimatedTime"));
		int cookingServings = Integer.parseInt(formData.get("cookingServings"));
		String category1Name = formData.get("category1Name");
		String category2Name = formData.get("category2Name");
		
		// ī�װ� ��ȸ
		int category1CD = recipeCategory1Service.getCategory1CDByName(category1Name);
		int category2CD = recipeCategory2Service.getCategory2CDByName(category2Name);
		
		RecipeVO recipeVO = new RecipeVO();
		recipeVO.setRecipeNM(recipeName);
		recipeVO.setRecipeDescription(recipeDescription);
		recipeVO.setEstimatedTime(estimatedTime);
		recipeVO.setCookingServings(cookingServings);
		recipeVO.setCategory1CD(category1CD);
		recipeVO.setCategory2CD(category2CD);
		
		if (imageFiles.containsKey("mainImage")) {// ���� �̹��� ����
		String mainImagePath = fileUpload.saveImage(imageFiles.get("mainImage").get(0), session, recipeName, null);
		    recipeVO.setRecipeMainImage(mainImagePath);
		}
		
	    recipeService.insertRecipe(recipeVO);
	    return recipeVO;
	}

	// 2. ��� ���� ����
	private void saveIngredients(int recipeCD, List<Map<String, String>> ingredients) {
	    for (Map<String, String> ingredient : ingredients) {
	        String ingredientName = ingredient.get("ingredientName");
	        String ingredientAmount = ingredient.get("ingredientAmount");
	        String ingredientType = ingredient.get("ingredientType");

	        // 1�� : ���� �̸��� ��ᰡ �ִ��� Ȯ��
	        IngredientVO existingIngredient = ingredientService.findByName(ingredientName);
	        int ingredientCD;

	        if (existingIngredient != null) {
	            // 1-1. ��ᰡ �����ϸ� ����� �⺻Ű�� ���
	            ingredientCD = existingIngredient.getIngredientCD();
	        } else {
	            // 2�� : ��ᰡ ������ ���ο� ��� ���
	            IngredientVO newIngredient = new IngredientVO();
	            newIngredient.setIngredientNM(ingredientName);
	            ingredientService.insertIngredient(newIngredient);

	            // ���� ��ϵ� ����� �⺻Ű �������� - MyBatis selectKey�� ���
	            ingredientCD = newIngredient.getIngredientCD();
	        }

	        // ��� ������ ���� IngredientTypeVO�� ����
	        IngredientTypeVO ingredientTypeVO = new IngredientTypeVO();
	        ingredientTypeVO.setRecipeCD(recipeCD);  // ������ ID
	        ingredientTypeVO.setIngredientCD(ingredientCD);  // ��� �⺻Ű
	        ingredientTypeVO.setIngredientAmt(ingredientAmount);  // ��� ��
	        ingredientTypeVO.setIngredientType(ingredientType);  // ��� ����

	        // ��� ������ ���� DB�� ����
	        ingredientTypeService.insertIngredientType(ingredientTypeVO);
	    }
	}

	// 3. ���� �ܰ� ���� ����
	private void saveSteps(int recipeCD, List<Map<String, String>> steps, List<MultipartFile> stepImages, HttpSession session, String recipeName) throws Exception {
	    for (int i = 0; i < steps.size(); i++) {
	        Map<String, String> step = steps.get(i);
	        RecipeStepVO stepVO = new RecipeStepVO();
	        stepVO.setRecipeCD(recipeCD);
	        stepVO.setStepORD(Integer.parseInt(step.get("stepsOrder")));
	        stepVO.setInstruction(step.get("stepsDescription"));

	        // ���� �ܰ� �̹��� ����
	        if (stepImages != null && i < stepImages.size()) {  // �̹����� �ִ��� Ȯ��
	            MultipartFile stepImage = stepImages.get(i);
	            if (!stepImage.isEmpty()) {
	                String stepImagePath = fileUpload.saveImage(stepImage, session, recipeName, i + 1);
	                stepVO.setImageURL(stepImagePath);
	            }
	        }

	        recipeStepService.insertRecipeStep(stepVO);
	    }
	}

    // 4. �� ���� ����
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
//        @RequestPart Map<String, String> formData,  // �Ϲ� �����͸� Map���� ����
//        @RequestPart("imageFiles") List<MultipartFile> imageFiles,  // �̹��� ���ϵ�
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
//        // ù ��° �̹����� ������ ���� �̹����� ����
//        if (!imageFiles.isEmpty()) {
//            String mainImagePath = fileUpload.saveImage(imageFiles.get(0), session, recipeName, null);  // ���� �̹���
//            recipeVO.setRecipeMainImage(mainImagePath);
//        }
//
//        // ������ ����
//        recipeService.insertRecipe(recipeVO);
//
//        // ��� ����
//        
//        //1�� : ���� �̸��� ��ᰡ �ֳ� Ȯ�� ������ ��� Ÿ�� ���̺��� �߰� ������ �ߺ� ���� �ޤ���
//        //2�� : ���� �̸��� ��ᰡ ������ ��Ḧ ��� ��� �⺻Ű�޾Ƽ� �� ����� �⺻Ű�� ���Ÿ�� ���̺� ����
//        for (int i = 0; i < ingredientNames.size(); i++) {
//            String ingredientName = ingredientNames.get(i);
//
//            // 1�� : ���� �̸��� ��ᰡ �ִ��� Ȯ��
//            IngredientVO existingIngredient = ingredientService.findByName(ingredientName);
//            int ingredientCD;
//
//            if (existingIngredient != null) {// 1-1. ��ᰡ �����ϸ� ����� �⺻Ű�� ���
//                ingredientCD = existingIngredient.getIngredientCD();
//            } else {// 2�� : ��ᰡ ������ ���ο� ��� ���
//                IngredientVO newIngredient = new IngredientVO();
//                newIngredient.setIngredientNM(ingredientName);
//                ingredientService.insertIngredient(newIngredient);
//
//                // ���� ��ϵ� ����� �⺻Ű ��������- selectKey�� �����
//                ingredientCD = newIngredient.getIngredientCD();
//            }
//
//            // ��� ������ ���� IngredientTypeVO�� ����
//            IngredientTypeVO ingredientTypeVO = new IngredientTypeVO();
//            ingredientTypeVO.setRecipeCD(recipeVO.getRecipeCD());  // ������ ID
//            ingredientTypeVO.setIngredientCD(ingredientCD);       // ��� �⺻Ű
//            ingredientTypeVO.setIngredientAmt(ingredientAmounts.get(i));  // ��� ��
//            ingredientTypeVO.setIngredientType(ingredientTypes.get(i));  // ��� ����
//
//            ingredientTypeService.insertIngredientType(ingredientTypeVO);
//        }
//
//        // ���� �ܰ� ����
//        for (int i = 0; i < steps.size(); i++) {
//            RecipeStepVO stepVO = new RecipeStepVO();
//            stepVO.setRecipeCD(recipeVO.getRecipeCD());
//            stepVO.setStepORD(i+1); // ���� ����
//            stepVO.setInstruction(steps.get(i));
//
//            // ���� �ܰ� �̹����� �ִ� ��� ó��
//            if (i + 1 < imageFiles.size()) {
//                String stepImagePath = fileUpload.saveImage(imageFiles.get(i + 1), session, recipeName, i + 1);  // ���� ��ȣ �߰�
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
//        @RequestPart Map<String, String> formData,  // �Ϲ� �����͸� Map���� ����
//        @RequestPart("imageFiles") List<MultipartFile> imageFiles,  // �̹��� ���ϵ�
//        HttpSession session) {
//
//    try {
//    	// 1. �Ϲ� ������ ó�� (Map���� ������)
//        String recipeName = formData.get("recipeName");
//        String recipeDescription = formData.get("recipeDescription");
//        int estimatedTime = Integer.parseInt(formData.get("estimatedTime"));
//        int cookingServings = Integer.parseInt(formData.get("cookingServings"));
//        
//        // ī�װ� �̸����� ī�װ� ID ��ȸ (�߰� �κ�)
//        String category1Name = formData.get("category1Name"); // �ѱ۷� ���� ī�װ� 1 �̸�
//        String category2Name = formData.get("category2Name"); // �ѱ۷� ���� ī�װ� 2 �̸�
//
//        // ī�װ� �̸����� ID ��ȸ ���� (DB���� �˻��ϰų� �������� ��� ����)
//        int category1CD = category1Service.getCategory1CDByName(category1Name); // ī�װ� 1 ID ��ȸ
//        int category2CD = category2Service.getCategory2CDByName(category2Name); // ī�װ� 2 ID ��ȸ
//
//        List<String> ingredientNames = List.of(formData.get("ingredientNames").split(","));  // ��� �̸� ����Ʈ�� ��ȯ
//        List<String> ingredientAmounts = List.of(formData.get("ingredientAmounts").split(","));  // ��� �� ����Ʈ�� ��ȯ
//        List<String> ingredientTypes = List.of(formData.get("ingredientTypes").split(","));  // ��� ���� ����Ʈ�� ��ȯ
//        List<String> steps = List.of(formData.get("steps").split(","));  // ���� �ܰ� ����Ʈ�� ��ȯ
//        List<String> tips = List.of(formData.get("tips").split(","));  // �� ����Ʈ�� ��ȯ
//        
//        // 2. ������ VO ���� �� ������ ����
//        RecipeVO recipeVO = new RecipeVO();
//        recipeVO.setRecipeNM(recipeName);
//        recipeVO.setRecipeDescription(recipeDescription);
//        recipeVO.setEstimatedTime(estimatedTime);
//        recipeVO.setCookingServings(cookingServings);
//        recipeVO.setCategory1CD(category1CD);
//        recipeVO.setCategory2CD(category2CD);
//
//        // 3. ù ��° �̹����� ������ ���� �̹����� ����
//        if (!imageFiles.isEmpty()) {
//            String mainImagePath = fileUpload.saveImage(imageFiles.get(0), session, recipeName, null);  // ���� �̹���
//            recipeVO.setRecipeMainImage(mainImagePath);
//        }
//
//        // 4. ������ ����
//        recipeService.insertRecipe(recipeVO);
//
//        // 5. ��� ����
//        for (int i = 0; i < ingredientNames.size(); i++) {
//            IngredientTypeVO ingredientTypeVO = new IngredientTypeVO();
//            ingredientTypeVO.setRecipeCD(recipeVO.getRecipeCD());  // ������ ID
//            ingredientTypeVO.setIngredientAmt(ingredientAmounts.get(i)); // ��� ��
//            ingredientTypeVO.setIngredientType(ingredientTypes.get(i));  // ��� ����
//            ingredientTypeService.insertIngredientType(ingredientTypeVO);
//        }
//
//        // 6. ���� �ܰ� ����
//        for (int i = 0; i < steps.size(); i++) {
//            RecipeStepVO stepVO = new RecipeStepVO();
//            stepVO.setRecipeCD(recipeVO.getRecipeCD());
//            stepVO.setStepORD(i + 1);  // ���� ����
//            stepVO.setInstruction(steps.get(i));
//
//            // ���� �ܰ� �̹����� �ִ� ��� ó��
//            if (i + 1 < imageFiles.size()) {
//                String stepImagePath = fileUpload.saveImage(imageFiles.get(i + 1), session, recipeName, i + 1);  // ���� ��ȣ �߰�
//                stepVO.setImageURL(stepImagePath);
//            }
//
//            recipeStepService.insertRecipeStep(stepVO);
//        }
//        
//     // 7. �� ����
//        for (int i = 0; i < tips.size(); i++) {
//            TipVO tipVO = new TipVO();
//            tipVO.setRecipeCD(recipeVO.getRecipeCD());  // ������ ID
//            tipVO.setTipContent(tips.get(i));           // �� ����
//            tipVO.setTipORD(i + 1);                     // �� ����
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
	
	
	
	
	
