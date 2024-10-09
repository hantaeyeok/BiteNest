import InputWithButton from './InputWithButton'
import Button from '../shared/Button'

function SubIngredientsSection({
  subIngredients,
  handleAddSubIngredient,
  handleSubIngredientChange,
  handleRemoveSubIngredient,
}: {
  subIngredients?: { name: string | null; quantity: string | null }[] | null
  handleAddSubIngredient: () => void
  handleSubIngredientChange: (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => void
  handleRemoveSubIngredient: (index: number) => void
}) {
  return (
    <div>
      <label className="block text-lg font-bold text-brown-300">부재료</label>
      <div className="flex flex-col space-y-2">
        {subIngredients &&
          subIngredients.map((ingredient, index) => (
            <InputWithButton
              key={index}
              value={ingredient?.name || ''}
              placeholder="예: 당근"
              quantityValue={ingredient?.quantity || ''}
              quantityPlaceholder="예: 1개"
              onChange={(e) =>
                handleSubIngredientChange(index, 'name', e.target.value)
              }
              onQuantityChange={(e) =>
                handleSubIngredientChange(index, 'quantity', e.target.value)
              }
              onRemove={() => handleRemoveSubIngredient(index)}
              disableRemove={false}
            />
          ))}
      </div>
      <Button
        label="추가"
        color="bg-cream text-white"
        size="full"
        onClick={handleAddSubIngredient}
      />
    </div>
  )
}

export default SubIngredientsSection
