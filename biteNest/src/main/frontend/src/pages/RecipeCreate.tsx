import Form from '@components/recipe/Form'
import instance from '@util/axios'
import { isAxiosError } from 'axios'

function RecipeCreatePage() {
  const handleSubmit = async () => {
    try {
      const response = await instance.post(
        'http://localhost:80/api/recipes/create',
        {
          title: 'New Recipe',
          description: 'This is a new recipe',
        },
      )
      console.log(response.data)
    } catch (error: any) {
      if (error.response) {
        // 서버 응답이 있었지만 상태 코드가 2xx 범위를 벗어났을 때
        console.error('서버 응답 오류:', error.response.data)
        console.error('상태 코드:', error.response.status)
        console.error('헤더:', error.response.headers)
      } else if (error.request) {
        // 요청이 전송되었으나 응답이 없을 때
        console.error('요청이 전송되었으나 응답이 없음:', error.request)
      } else {
        // 요청을 설정하는 도중 오류가 발생했을 때
        console.error('Axios 설정 오류:', error.message)
      }
      console.error('전체 오류 객체:', error)
    }
  }

  return (
    <div>
      <Form onSubmit={handleSubmit} />
    </div>
  )
}

export default RecipeCreatePage
