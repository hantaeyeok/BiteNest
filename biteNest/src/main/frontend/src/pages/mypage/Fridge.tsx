import { useEffect, useState } from 'react'
import axios from 'axios' // Axios로 API 호출을 관리
import { Ingredient } from '@models/ingredient'
import IngredientItem from '@/components/fridge/shared/ingredientItem'
import useUser from '@hooks/auth/useUser'

function FridgePage() {
  const { user } = useUser() // Recoil 혹은 다른 전역 상태에서 user id 가져오기
  const [ingredients, setIngredients] = useState<Ingredient[]>([])
  const [loading, setLoading] = useState<boolean>(true)

  useEffect(() => {
    if (user && user.id) {
      // Postman API로부터 데이터 호출 - user id를 쿼리 파라미터로 전달
      axios
        .get(
          `https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/fridge-ingredients`,
          {
            params: { userId: user.id }, // userId를 쿼리 파라미터로 넘김
          },
        )
        .then((response) => {
          setIngredients(response.data) // API에서 받은 데이터를 상태에 저장
          setLoading(false) // 로딩 상태 해제
        })
        .catch((error) => {
          console.error('Error fetching data:', error)
          setLoading(false) // 로딩 상태 해제
        })
    }
  }, [user]) // user.id가 변경되면 다시 API 호출

  if (loading) {
    return <div>Loading...</div> // 데이터 로딩 중일 때 보여줄 메시지
  }

  return (
    <div>
      <div className="grid grid-cols-1 gap-4">
        {ingredients.length > 0 ? (
          ingredients.map((ingredient) => (
            <IngredientItem key={ingredient.id} ingredient={ingredient} /> // 각각의 재료 아이템을 렌더링
          ))
        ) : (
          <div>냉장고에 저장된 재료가 없습니다.</div> // 데이터가 없을 때 표시할 메시지
        )}
      </div>
    </div>
  )
}

export default FridgePage
