import { useState } from 'react'
import axios from 'axios'
import Button from '@/components/shared/Button'

interface FridgeItemDetailsProps {
  ingredient: {
    id: number
    name: string
    iconNumber: number
    registrationDate: string
    expirationDate: string
  }
}

function FridgeItemDetails({ ingredient }: FridgeItemDetailsProps) {
  const [selectedIcon, setSelectedIcon] = useState<number>(
    ingredient.iconNumber,
  ) // 아이콘 넘버 상태
  const [registrationDate, setRegistrationDate] = useState<string>(
    ingredient.registrationDate,
  ) // 등록일 상태
  const [expirationDate, setExpirationDate] = useState<string>(
    ingredient.expirationDate,
  ) // 유통기한 상태

  const handleIconSelect = (iconValue: number) => {
    setSelectedIcon(iconValue) // 아이콘 넘버 저장
  }

  const handleSave = () => {
    // 서버로 선택된 데이터를 전송하는 로직을 추가할 수 있습니다.
    console.log('선택된 아이콘 번호:', selectedIcon)
    console.log('등록일:', registrationDate)
    console.log('유통기한:', expirationDate)

    // 예시로 Axios를 사용해 서버로 데이터를 전송하는 방식
    axios
      .post('/api/save-ingredient-details', {
        id: ingredient.id,
        iconNumber: selectedIcon,
        registrationDate,
        expirationDate,
      })
      .then(() => {
        alert('저장되었습니다!')
      })
      .catch((error) => {
        console.error('저장 중 오류 발생:', error)
      })
  }

  return (
    <div className="mt-6">
      {/* 아이콘 선택 영역 */}
      <div className="grid grid-cols-4 gap-4 mb-4">
        {[1, 2, 3, 4, 5, 6, 7, 8].map((icon) => (
          <button
            key={icon}
            onClick={() => handleIconSelect(icon)}
            className={`p-2 border rounded-lg w-20 h-20 ${
              selectedIcon === icon ? 'bg-brown-100' : 'border-brown-100'
            }`}
          >
            {/* Placeholder for icons - Here you can replace with actual SVG icons */}
            <span className="text-2xl">
              {icon === 1 ? '🥦' : icon === 2 ? '🥚' : icon === 3 ? '🥛' : '🍖'}
            </span>
          </button>
        ))}
      </div>

      {/* 재료 이름 */}
      <h2 className="text-center text-xl font-bold mb-4">{ingredient.name}</h2>

      {/* 등록일과 유통기한 입력 필드 */}
      <div className="mb-4">
        <label className="block text-sm font-semibold mb-1">등록날짜</label>
        <input
          type="date"
          value={registrationDate}
          onChange={(e) => setRegistrationDate(e.target.value)} // 등록일 업데이트
          className="w-full p-2 border border-gray-300 rounded-lg"
        />
      </div>
      <div className="mb-6">
        <label className="block text-sm font-semibold mb-1">유통기한</label>
        <input
          type="date"
          value={expirationDate}
          onChange={(e) => setExpirationDate(e.target.value)} // 유통기한 업데이트
          className="w-full p-2 border border-gray-300 rounded-lg"
        />
      </div>

      {/* 저장 버튼 */}
      <div className="text-center">
        <Button label="저장" size="full" onClick={handleSave} />
      </div>
    </div>
  )
}

export default FridgeItemDetails
