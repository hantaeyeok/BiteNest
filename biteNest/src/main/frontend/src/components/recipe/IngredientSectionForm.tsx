import InputWithButton from './InputWithButton'
import Button from '../shared/Button'

function IngredientsSection({
  ingredients,
  handleAddIngredient,
  handleIngredientChange,
  handleRemoveIngredient,
}: {
  ingredients: { name: string; quantity: string }[]
  handleAddIngredient: () => void
  handleIngredientChange: (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => void
  handleRemoveIngredient: (index: number) => void
}) {
  return (
    <div>
      <label className="block text-lg font-bold text-brown-300">
        필수재료*
      </label>
      <div className="flex flex-col space-y-2">
        {ingredients.map((ingredient, index) => (
          <InputWithButton
            key={index}
            value={ingredient.name}
            placeholder="예: 당근"
            quantityValue={ingredient.quantity}
            quantityPlaceholder="예: 1개"
            onChange={(e) =>
              handleIngredientChange(index, 'name', e.target.value)
            }
            onQuantityChange={(e) =>
              handleIngredientChange(index, 'quantity', e.target.value)
            }
            onRemove={() => handleRemoveIngredient(index)}
            disableRemove={ingredients.length === 1}
          />
        ))}
      </div>
      <Button
        label="추가"
        color="bg-salmon-200 text-salmon-100"
        size="full"
        onClick={handleAddIngredient}
      />
    </div>
  )
}

export default IngredientsSection
