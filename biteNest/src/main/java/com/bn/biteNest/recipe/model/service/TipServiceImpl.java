package com.bn.biteNest.recipe.model.service;


import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.bn.biteNest.recipe.model.dao.TipMapper;
import com.bn.biteNest.recipe.model.vo.TipVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipServiceImpl implements TipService {

    private final TipMapper tipMapper;

    @Override
    public boolean insertTip(TipVO tip) {
        return tipMapper.insertTip(tip) > 0;
    }

    @Override
    public boolean updateTipsByRecipe(int recipeCD, List<Map<String, String>> tips) {
        try {
            List<TipVO> existingTips = getTipsByRecipeId(recipeCD);

            for (Map<String, String> tipData : tips) {
                int tipOrder = Integer.parseInt(tipData.get("tipOrder"));
                String tipContent = tipData.get("tipContent");

                TipVO tipVO = TipVO.builder()
			                       .recipeCD(recipeCD)
			                       .tipORD(tipOrder)
			                       .tipContent(tipContent)
			                       .build();

                TipVO existingTip = existingTips.stream()
						                        .filter(e -> e.getTipORD() == tipOrder)
						                        .findFirst()
						                        .orElse(null);

                boolean isUpdated = (existingTip != null)
                        ? tipMapper.updateTip(tipVO) > 0
                        : tipMapper.insertTip(tipVO) > 0;
                        
				if (!isUpdated) {
			        log.error("Failed to insert/update tip: {}", tipVO);
			        return false;
			    }
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to update tips for recipe ID {}: ", recipeCD, e);
            return false;
        }
    }

    @Override
    public List<TipVO> getTipsByRecipeId(int recipeCD) {
        try {
            return tipMapper.selectTipsByRecipeId(recipeCD);
        } catch (Exception e) {
            log.error("Failed to fetch tips for recipe ID: {}", recipeCD, e);
            throw new RuntimeException("Failed to fetch tips for recipe ID: " + recipeCD);
        }
    }


    @Override
    public boolean deleteTipsByRecipeCD(int recipeCD) {
        try {
            int deletedCount = tipMapper.deleteTipsByRecipeCD(recipeCD);
            log.info("Deleted {} tips for recipe ID: {}", deletedCount, recipeCD);
            return deletedCount > 0;
        } catch (Exception e) {
            log.error("Failed to delete tips for recipe ID: {}", recipeCD, e);
            return false;
        }
    }
}