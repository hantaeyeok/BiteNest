import { ChangeEvent } from 'react'

interface RadioButtonProps {
  label: string
  name: string
  value: number
  onChange: (e: ChangeEvent<HTMLInputElement>) => void
}

function RadioButton({ label, name, value, onChange }: RadioButtonProps) {
  return (
    <label className="cursor-pointer flex items-center justify-center h-10 w-1/4 border border-brown-100 rounded-md hover:bg-brown-100 hover:text-white">
      <input
        type="radio"
        name={name}
        value={value}
        className="hidden peer"
        required
        onChange={onChange}
      />
      <span className="peer-checked:text-white peer-checked:bg-brown-100 peer-checked:border border-brown-100 w-full h-full flex items-center justify-center rounded-sm  ">
        {label}
      </span>
    </label>
  )
}

export default RadioButton
