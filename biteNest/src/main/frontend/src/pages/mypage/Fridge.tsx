import { useEffect, useState } from 'react'
import axios from 'axios'
import { Ingredient } from '@models/ingredient'
import IngredientItem from '@/components/fridge/shared/ingredientItem'
import useUser from '@hooks/auth/useUser'
import Button from '@/components/shared/Button'
import Modal from '@shared/Modal'
import FridgeForm from '@/components/fridge/FridgeForm'
import FridgeItemDetails from '@/components/fridge/FridgeItemDetails' // 새로운 컴포넌트
import instance from '@/util/axios'

function FridgePage() {
  const { user } = useUser()
  const [ingredients, setIngredients] = useState<Ingredient[]>([])
  const [loading, setLoading] = useState<boolean>(true)
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false) // 재료 추가 모달 상태
  const [selectedIngredient, setSelectedIngredient] =
    useState<Ingredient | null>(null) // 선택된 재료
  const [isDetailModalOpen, setIsDetailModalOpen] = useState<boolean>(false) // 상세 모달 상태

  useEffect(() => {
    if (user && user.id) {
      instance
        .get(
          'https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/fridge-ingredients',
          {
            params: { userId: user.id },
          },
        )
        .then((response) => {
          setIngredients(response.data)
          setLoading(false)
        })
        .catch((error) => {
          console.error('Error fetching data:', error)
          setLoading(false)
        })
    }
  }, [user])

  // 모달 열기/닫기 핸들러
  const handleOpenModal = () => {
    setIsModalOpen(true) // 재료 추가 모달 열기
  }

  const handleCloseModal = () => {
    setIsModalOpen(false) // 재료 추가 모달 닫기
  }

  const handleIngredientClick = (ingredient: Ingredient) => {
    setSelectedIngredient(ingredient) // 선택된 재료 저장
    setIsDetailModalOpen(true) // 상세 모달 열기
  }

  const handleCloseDetailModal = () => {
    setIsDetailModalOpen(false) // 상세 모달 닫기
    setSelectedIngredient(null) // 선택된 재료 초기화
  }

  if (loading) {
    return <div>Loading...</div>
  }

  return (
    <div className="relative h-full w-full min-h-[500px]">
      <div className="flex justify-end">
        <Button
          label="재료 등록하기"
          size="small"
          onClick={handleOpenModal}
        ></Button>{' '}
      </div>
      <div className="my-6"></div>
      <div className="grid grid-cols-1 gap-4">
        {ingredients.length > 0 ? (
          ingredients.map((ingredient) => (
            <IngredientItem
              key={ingredient.id}
              ingredient={ingredient}
              onClick={() => handleIngredientClick(ingredient)} // 재료 클릭 시 상세 모달 열기
            />
          ))
        ) : (
          <div>냉장고에 저장된 재료가 없습니다.</div>
        )}
      </div>
      <div className="absolute w-full bottom-1">
        <Button
          label="나의 맞춤 레시피"
          size="full"
          color="bg-green-100"
        ></Button>
      </div>

      {/* 재료 등록 모달 */}
      <Modal isOpen={isModalOpen} onClose={handleCloseModal}>
        <FridgeForm />
      </Modal>

      {/* 재료 상세 정보 모달 */}
      {selectedIngredient && (
        <Modal isOpen={isDetailModalOpen} onClose={handleCloseDetailModal}>
          <FridgeItemDetails
            ingredient={{
              ...selectedIngredient,
              registrationDate: selectedIngredient.registrationDate || '', // 기본값 사용
              expirationDate: selectedIngredient.expirationDate || '', // 기본값 사용
            }}
          />
        </Modal>
      )}
    </div>
  )
}

export default FridgePage
