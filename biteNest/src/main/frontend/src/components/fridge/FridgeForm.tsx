import { useEffect, useState } from 'react'
import instance from '@/util/axios'
import Button from '../shared/Button'
import { Ingredient } from '@models/ingredient'

function FridgeForm() {
  const [ingredients, setIngredients] = useState<Ingredient[]>([])
  const [selectedIngredients, setSelectedIngredients] = useState<Ingredient[]>(
    [],
  ) // 선택된 재료 상태
  const [loading, setLoading] = useState<boolean>(true)
  const [searchTerm, setSearchTerm] = useState<string>('') // 검색어 상태
  const [displayedIngredients, setDisplayedIngredients] = useState<
    Ingredient[]
  >([]) // 화면에 표시되는 재료
  const [page, setPage] = useState<number>(1) // 페이지 관리 (무한 스크롤)
  const pageSize = 20 // 한 번에 보여줄 재료 수

  useEffect(() => {
    // 초기 데이터 로드
    instance
      .get(
        'https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/ingredientsList',
      )
      .then((response) => {
        setIngredients(response.data)
        setDisplayedIngredients(response.data.slice(0, pageSize)) // 처음에 일부 데이터만 보여줌
        setLoading(false)
      })
      .catch((error) => {
        console.error('Error fetching data:', error)
        setLoading(false)
      })
  }, [])

  useEffect(() => {
    // 스크롤 이벤트 등록
    const handleScroll = () => {
      const scrollTop = document.documentElement.scrollTop
      const windowHeight = window.innerHeight
      const docHeight = document.documentElement.offsetHeight

      if (scrollTop + windowHeight >= docHeight - 10 && !loading) {
        loadMore()
      }
    }

    window.addEventListener('scroll', handleScroll)

    return () => {
      window.removeEventListener('scroll', handleScroll)
    }
  }, [loading, displayedIngredients, ingredients])

  const loadMore = () => {
    // 추가 데이터 로드
    if (displayedIngredients.length < ingredients.length) {
      const nextPage = page + 1
      const newIngredients = ingredients.slice(0, nextPage * pageSize)
      setDisplayedIngredients(newIngredients)
      setPage(nextPage)
    }
  }

  const handleSearch = () => {
    if (searchTerm === '') {
      // 검색어가 없으면 기본 목록 표시
      setDisplayedIngredients(ingredients.slice(0, pageSize))
    } else {
      const filtered = ingredients.filter((ingredient) =>
        ingredient.name.includes(searchTerm),
      )
      setDisplayedIngredients(filtered.slice(0, pageSize)) // 검색된 재료만 표시
    }
    setPage(1) // 검색 시 페이지를 1로 초기화
  }

  const handleSelect = (ingredient: Ingredient) => {
    const isSelected = selectedIngredients.some(
      (item) => item.id === ingredient.id,
    )
    if (isSelected) {
      setSelectedIngredients(
        selectedIngredients.filter((item) => item.id !== ingredient.id),
      ) // 선택 해제
    } else {
      setSelectedIngredients([...selectedIngredients, ingredient]) // 선택 추가
    }
  }

  const handleClearSearch = () => {
    // 검색어 삭제
    setSearchTerm('')
    setDisplayedIngredients(ingredients.slice(0, pageSize)) // 기본 재료 리스트 표시
  }

  const handleSubmit = () => {
    if (selectedIngredients.length === 0) {
      alert('선택된 재료가 없습니다.')
      return
    }

    // 서버로 선택된 재료를 POST 요청으로 전송
    instance
      .post('/api/submit-ingredients', {
        ingredients: selectedIngredients.map((ingredient) => ingredient.id), // 재료 id만 전송
      })
      .then((response) => {
        // 성공적으로 전송되었을 경우 처리
        alert('재료가 성공적으로 추가되었습니다!')
        // 전송 후 선택된 재료 초기화
        setSelectedIngredients([])
      })
      .catch((error) => {
        // 에러가 발생했을 경우 처리
        console.error('Error submitting ingredients:', error)
        alert('재료 추가 중 오류가 발생했습니다.')
      })
  }

  return (
    <div>
      {/* 검색 필드 */}
      <div className="flex justify-center items-center relative">
        <input
          placeholder="재료명을 입력해 검색해보세요"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)} // 검색어 입력 상태 업데이트
          className="w-3/4 h-10 border border-brown-100 p-2 m-2 rounded-lg"
        />
        {searchTerm && (
          <button
            onClick={handleClearSearch}
            className="absolute right-20 text-gray-400 hover:text-gray-600"
          >
            X
          </button>
        )}
        <Button label="검색" onClick={handleSearch} /> {/* 검색 버튼 */}
      </div>

      {/* 선택된 재료 */}
      <div className="flex flex-wrap gap-2 justify-center items-center mb-4">
        {selectedIngredients.map((ingredient) => (
          <div
            key={ingredient.id}
            className="bg-green-100 text-white p-2 rounded-lg cursor-pointer flex items-center"
            onClick={() => handleSelect(ingredient)}
          >
            {ingredient.name} <span className="ml-2">X</span>{' '}
            {/* 선택 해제 버튼 */}
          </div>
        ))}
      </div>

      {/* 재료 목록 */}
      <div className="grid grid-cols-3 gap-2 max-h-96 overflow-auto">
        {loading ? (
          <div>로딩 중...</div>
        ) : displayedIngredients.length > 0 ? (
          displayedIngredients.map((ingredient) => (
            <div
              key={ingredient.id}
              className={`p-2 border rounded-lg cursor-pointer ${
                selectedIngredients.some((item) => item.id === ingredient.id)
                  ? 'bg-brown-100 text-white'
                  : 'bg-white text-brown-500'
              }`}
              onClick={() => handleSelect(ingredient)}
            >
              {ingredient.name}
            </div>
          ))
        ) : (
          <div>재료가 없습니다.</div>
        )}
      </div>

      {/* 선택한 재료 개수 및 추가 버튼 */}
      <div className="flex justify-center mt-4">
        <Button
          onClick={handleSubmit}
          label={`총 ${selectedIngredients.length}개의 재료 추가`}
          size="full"
          color="bg-green-100"
        />
      </div>
    </div>
  )
}

export default FridgeForm
