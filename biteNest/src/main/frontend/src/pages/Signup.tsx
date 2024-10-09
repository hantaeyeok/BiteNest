import instance from '@util/axios'
import Form from '@components/signup/Form'
import { FormValues } from '@models/signup'
import { isAxiosError } from 'axios'
import { useNavigate } from 'react-router-dom'

function SignupPage() {
  const navigate = useNavigate()

  const handleSubmit = async (formValues: FormValues) => {
    try {
      const response = await instance.post('/user/signup', formValues)
      console.log('응답 데이터:', response.data)

      // 회원가입 성공 후 로그인 페이지로 리다이렉트
      navigate('/signin')
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

export default SignupPage
