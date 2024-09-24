import InputWithButton from './InputWithButton'
import Button from '../shared/Button'

function SauceSection({
  sauce,
  handleAddSauce,
  handleSauceChange,
  handleRemoveSauce,
}: {
  sauce?: { name: string | null; quantity: string | null }[] | null
  handleAddSauce: () => void
  handleSauceChange: (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => void
  handleRemoveSauce: (index: number) => void
}) {
  return (
    <div>
      <label className="block text-lg font-bold text-brown-300">소스</label>
      <div className="flex flex-col space-y-2">
        {sauce &&
          sauce.map((sauceItem, index) => (
            <InputWithButton
              key={index}
              value={sauceItem?.name || ''}
              placeholder="예: 간장"
              quantityValue={sauceItem?.quantity || ''}
              quantityPlaceholder="예: 2큰술"
              onChange={(e) => handleSauceChange(index, 'name', e.target.value)}
              onQuantityChange={(e) =>
                handleSauceChange(index, 'quantity', e.target.value)
              }
              onRemove={() => handleRemoveSauce(index)}
              disableRemove={false}
            />
          ))}
      </div>
      <Button
        label="추가"
        color="bg-salmon-200 text-salmon-100"
        size="full"
        onClick={handleAddSauce}
      />
    </div>
  )
}

export default SauceSection
