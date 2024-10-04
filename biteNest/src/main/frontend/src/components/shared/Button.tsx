interface ButtonProps {
  label: string
  onClick?: () => void
  type?: 'button' | 'submit' | 'reset'
  color?: string // 버튼 색상
  size?: 'small' | 'medium' | 'large' | 'full' // 버튼 크기
  disabled?: boolean
}

function Button({
  label,
  onClick,
  type = 'button',
  color = 'bg-brown-100',
  size,
  disabled = false,
}: ButtonProps) {
  const baseStyle =
    'font-semibold rounded-lg focus:outline-none focus:ring transition duration-300 ease-in-out hover:bg-opacity-70'

  // 크기 설정
  const sizeStyle =
    size === 'small'
      ? 'px-3 py-2 text-sm'
      : size === 'large'
        ? 'px-6 py-3 text-lg'
        : size === 'full'
          ? 'w-full px-4 py-2 text-md'
          : 'px-4 py-2 text-md my-3' // 기본은 medium

  // disabled일 때 투명도를 설정
  const disabledStyle = disabled ? 'bg-opacity-50 cursor-not-allowed' : ''

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`${baseStyle} ${sizeStyle} ${color} text-white ${disabledStyle}`}
    >
      {label}
    </button>
  )
}

export default Button
