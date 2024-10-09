import Form from '@components/recipe/Form'
import instance from '@util/axios'
import { isAxiosError } from 'axios'
import { FormValues } from '@/models/recipe'

function RecipeCreatePage() {
  const handleSubmit = async (formData: FormData) => {
    // FormData의 내용을 출력하는 부분
    formData.forEach((value, key) => {
      console.log(key, value)
    })
    //   try {
    //     const response = await instance.post('/api/recipe-create', formValues)
    //     console.log('응답 데이터:', response.data)
    //   } catch (error) {
    //     if (isAxiosError(error)) {
    //       console.error('Axios 오류 발생:', error.response?.data)
    //     } else {
    //       console.error('오류 발생:', error)
    //     }
    //   }
  }
  return (
    <div>
      <Form onSubmit={handleSubmit} />
    </div>
  )
}

export default RecipeCreatePage
