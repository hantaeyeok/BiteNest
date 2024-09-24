import Button from '../shared/Button'

function InputWithButton({
  value,
  placeholder,
  onChange,
  onRemove,
  disableRemove,
  quantityValue,
  quantityPlaceholder,
  onQuantityChange,
}: {
  value: string | null
  placeholder: string
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
  onRemove: () => void
  disableRemove: boolean
  quantityValue: string | null
  quantityPlaceholder: string
  onQuantityChange: (e: React.ChangeEvent<HTMLInputElement>) => void
}) {
  return (
    <div className="flex space-x-2">
      <input
        type="text"
        className="mt-1 p-2 border border-brown-100 w-2/3 rounded-md focus:outline-none focus:border-brown-300 focus:ring-brown-300"
        placeholder={placeholder}
        value={value || ''}
        onChange={onChange}
      />
      <input
        type="text"
        className="mt-1 p-2 border border-brown-100 w-1/3 rounded-md focus:outline-none focus:border-brown-300 focus:ring-brown-300"
        placeholder={quantityPlaceholder}
        value={quantityValue || ''}
        onChange={onQuantityChange}
      />
      <Button
        label="X"
        color="bg-salmon-200 text-white"
        size="small"
        onClick={onRemove}
        disabled={disableRemove}
      />
    </div>
  )
}

export default InputWithButton
