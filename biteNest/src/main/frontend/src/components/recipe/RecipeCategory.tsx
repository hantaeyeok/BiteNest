import { useState, useEffect } from 'react'

interface Category {
  id: number
  name: string
}

function RecipeCategorySelect() {
  const [cuisineCategories, setCuisineCategories] = useState<Category[]>([])
  const [mealCategories, setMealCategories] = useState<Category[]>([])
  const [loading, setLoading] = useState(true)

  // 카테고리 데이터를 서버에서 가져오는 함수
  const fetchCategories = async () => {
    try {
      const cuisineResponse = await fetch(
        'https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/cuisine-categories',
      ) // 요리 종류 API
      const mealResponse = await fetch(
        'https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/meal-categories',
      ) // 식사 종류 API

      if (!cuisineResponse.ok || !mealResponse.ok) {
        throw new Error('데이터를 가져오는 데 오류가 발생했습니다.')
      }

      const cuisineData = await cuisineResponse.json()
      const mealData = await mealResponse.json()

      setCuisineCategories(cuisineData)
      setMealCategories(mealData)
    } catch (error) {
      console.error('Error fetching categories:', error)
    } finally {
      setLoading(false)
    }
  }
  useEffect(() => {
    fetchCategories()
  }, [])

  if (loading) {
    return <p>로딩 중...</p>
  }

  return (
    <div className="flex flex space-x-4">
      <div className="w-1/2">
        <select
          id="cuisine-type"
          className="w-full mt-1 p-2 border border-brown-100 rounded-md focus:outline-none focus:border-brown-300 focus:ring-brown-300"
        >
          <option value="">요리 분류를 선택하세요</option>
          {cuisineCategories.map((category) => (
            <option key={category.id} value={category.name}>
              {category.name}
            </option>
          ))}
        </select>
      </div>

      <div className="w-1/2">
        <select
          id="meal-type"
          className="w-full mt-1 p-2 border border-brown-100 rounded-md focus:outline-none focus:border-brown-300 focus:ring-brown-300"
        >
          <option value="">요리 종류를 선택하세요</option>
          {mealCategories.map((category) => (
            <option key={category.id} value={category.name}>
              {category.name}
            </option>
          ))}
        </select>
      </div>
    </div>
  )
}

export default RecipeCategorySelect
