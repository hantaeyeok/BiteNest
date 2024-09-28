import { Ingredient } from '@models/ingredient'

function IngredientItem({ ingredient }: { ingredient: Ingredient }) {
  const icons: { [key: number]: JSX.Element } = {
    1: (
      <svg
        width="50"
        height="50"
        viewBox="0 0 24 24"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <circle cx="12" cy="12" r="10" fill="red" />
        <path d="M12 2L12 12L22 12" stroke="white" strokeWidth="2" />
      </svg>
    ), // Tomato SVG
    2: (
      <svg
        width="50"
        height="50"
        viewBox="0 0 24 24"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <rect
          width="14"
          height="18"
          x="5"
          y="3"
          fill="white"
          stroke="black"
          strokeWidth="2"
        />
        <rect width="8" height="5" x="8" y="2" fill="gray" />
      </svg>
    ), // Milk SVG
    3: (
      <svg
        width="50"
        height="50"
        viewBox="0 0 24 24"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M12 2L15 8H9L12 2Z"
          fill="yellow"
          stroke="black"
          strokeWidth="2"
        />
        <circle
          cx="12"
          cy="16"
          r="6"
          fill="white"
          stroke="black"
          strokeWidth="2"
        />
      </svg>
    ), // Chicken SVG
    4: (
      <svg
        width="50"
        height="50"
        viewBox="0 0 24 24"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M12 2C14 2 16 4 16 6C16 8 14 10 12 10C10 10 8 8 8 6C8 4 10 2 12 2Z"
          fill="orange"
        />
        <rect x="10" y="10" width="4" height="12" fill="green" />
      </svg>
    ), // Carrot SVG
  }
  const icon = icons[ingredient.iconNumber] || (
    <svg
      width="50"
      height="50"
      viewBox="0 0 24 24"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <circle cx="12" cy="12" r="10" fill="gray" />
    </svg>
  ) // 기본 아이콘

  return (
    <div className="material-item bg-white mb-4 rounded-lg flex p-4">
      <div className="flex justify-center item-center border border-brown-100 p-2 rounded-lg mr-6">
        {icon}
      </div>
      <div>
        <strong>{ingredient.name}</strong>
        <div className="text-sm">
          <p>등록날짜: {ingredient.registrationDate}</p>
          <p>유통기한: {ingredient.expirationDate}</p>
        </div>
      </div>
    </div>
  )
}

export default IngredientItem
