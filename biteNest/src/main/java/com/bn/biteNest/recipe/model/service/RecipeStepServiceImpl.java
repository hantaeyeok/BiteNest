package com.bn.biteNest.recipe.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bn.biteNest.common.RecipeFileDelete;
import com.bn.biteNest.common.RecipeFileUpload;
import com.bn.biteNest.recipe.model.dao.RecipeStepMapper;
import com.bn.biteNest.recipe.model.vo.RecipeStepVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeStepServiceImpl implements RecipeStepService { 

    private final RecipeStepMapper recipeStepMapper;
    private final RecipeFileUpload fileUpload;
    private final RecipeFileDelete fileDelete;
    @Override
    public boolean insertRecipeStep(RecipeStepVO step) {
        return recipeStepMapper.insertRecipeStep(step) > 0;
    }

    @Override
    public boolean updateRecipeSteps(int recipeCD, 
    								 List<Map<String, String>> steps, 
    								 List<MultipartFile> stepImages, 
    								 String recipeName) {
        try {
        	
            // 기존 단계 목록 조회 후 Map으로 변환
            Map<Integer, RecipeStepVO> existingSteps = new HashMap<>();
        	List<RecipeStepVO> stepList = getRecipeStepsByRecipeCD(recipeCD);
        	for (RecipeStepVO step : stepList) {
        		existingSteps.put(step.getStepORD(), step);
        	}
        	
            for (int i = 0; i < steps.size(); i++) {
                Map<String, String> stepData = steps.get(i);
                int stepOrder = Integer.parseInt(stepData.get("stepsOrder"));
                String instruction = stepData.get("stepsDescription");

                // 새로운 RecipeStepVO 생성
                RecipeStepVO updatedStep = RecipeStepVO.builder()
								                       .recipeCD(recipeCD)
								                       .stepORD(stepOrder)
								                       .instruction(instruction)
								                       .imageURL(Optional.ofNullable(getImagePath(stepImages, i, recipeName)).orElse(null))
								                       .build();

                // 기존 단계가 존재하면 업데이트, 그렇지 않으면 새로 추가
                if (existingSteps.containsKey(stepOrder)) {
                    // 이미지 파일이 변경되었다면 기존 이미지 삭제
                    RecipeStepVO existingStep = existingSteps.get(stepOrder);
                    if (updatedStep.getImageURL() != null && existingStep.getImageURL() != null) {
                        fileDelete.deleteFile(existingStep.getImageURL());
                    }
                    recipeStepMapper.updateRecipeStep(updatedStep);
                } else {
                    recipeStepMapper.insertRecipeStep(updatedStep);
                }
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to update steps for Recipe CD {}: ", recipeCD, e);
            return false;
        }

    }

    private String getImagePath(List<MultipartFile> stepImages, int index, String recipeName) {
        if (stepImages != null && index < stepImages.size() && !stepImages.get(index).isEmpty()) {
            return fileUpload.saveImage(stepImages.get(index), recipeName, index + 1);
        }
        return null;
    }

	@Override
    public List<RecipeStepVO> getRecipeStepsByRecipeCD(int recipeCD) {
        return recipeStepMapper.selectRecipeStepByRecipeCD(recipeCD);
    }

    @Override
    public boolean deleteStepsByRecipeCD(int recipeCD) {
    	try {
            // 단계별로 삭제 전에 기존 이미지 파일 삭제
            List<RecipeStepVO> steps = getRecipeStepsByRecipeCD(recipeCD);
            for (RecipeStepVO step : steps) {
                fileDelete.deleteFile(step.getImageURL());
            }

            recipeStepMapper.deleteStepsByRecipeCD(recipeCD);
            return true;
        } catch (Exception e) {
            log.error("Failed to delete recipe steps for recipeCD: " + recipeCD, e);
            return false;
        }
    }
    
}
