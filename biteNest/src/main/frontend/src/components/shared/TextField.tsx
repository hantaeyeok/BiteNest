import {
  FocusEventHandler,
  forwardRef,
  InputHTMLAttributes,
  useState,
} from 'react'

interface TextFieldProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: React.ReactNode
  hasError?: boolean
  helpMessage?: React.ReactNode
}

const TextField = forwardRef<HTMLInputElement, TextFieldProps>(
  function TextField(
    { label, placeholder, helpMessage, onFocus, onBlur, hasError, ...props },
    ref,
  ) {
    const [focused, setFocused] = useState(false)
    const labelColor = hasError
      ? 'text-red'
      : focused
        ? 'text-textBrown-100'
        : 'text-brown-100'

    const handleFocus: FocusEventHandler<HTMLInputElement> = (event) => {
      setFocused(true)
      onFocus?.(event)
    }
    const handleBlur: FocusEventHandler<HTMLInputElement> = (event) => {
      setFocused(false)
      onBlur?.(event)
    }
    const inputClassName = `
  mt-1 block w-full px-3 py-2 bg-white border border-brown rounded-md text-sm shadow-sm placeholder-grey
  focus:outline-none focus:border-brown-300 focus:ring-1 focus:ring-brown-300
  disabled:bg-cream disabled:text-cream disabled:border-brown-200 disabled:shadow-none
  invalid:border-red invalid:text-red
  focus:invalid:red focus:invalid:red
`
    return (
      <div className="w-full">
        {label ? <div className={`${labelColor} text-sm`}>{label}</div> : null}
        <input
          type="text"
          placeholder={placeholder}
          className={inputClassName}
          ref={ref}
          aria-invalid={hasError}
          onFocus={handleFocus}
          onBlur={handleBlur}
          {...props}
        />

        {helpMessage ? (
          <div className={`${labelColor} text-xs my-2`}>{helpMessage}</div>
        ) : null}
      </div>
    )
  },
)

export default TextField
