import Form from '@components/recipe/Form'
import instance from '@util/axios'
import { isAxiosError } from 'axios'

function RecipeCreatePage() {
  const handleSubmit = async (formData: FormData) => {
    // FormData의 내용을 출력하는 부분 (디버깅용)
    formData.forEach((value, key) => {
      console.log(key, value)
    })

    try {
      // 서버에서 기대하는 "multipart/form-data" 형식으로 전송
      const response = await instance.post('/recipes/create', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      console.log('응답 데이터:', response.data)
    } catch (error) {
      if (isAxiosError(error)) {
        console.error('Axios 오류 발생:', error.response?.data)
      } else {
        console.error('오류 발생:', error)
      }
    }
  }

  return (
    <div>
      <Form onSubmit={handleSubmit} />
    </div>
  )
}

export default RecipeCreatePage
