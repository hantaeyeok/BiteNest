import axios from 'axios'
import Form from '@components/signup/Form'
import { FormValues } from '@models/signup'

function SignupPage() {
  const handleSubmit = async (formValues: FormValues) => {
    try {
      const response = await axios.post('/signup', formValues)
      console.log('응답 데이터:', response.data)
    } catch (error) {
      if (axios.isAxiosError(error)) {
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
