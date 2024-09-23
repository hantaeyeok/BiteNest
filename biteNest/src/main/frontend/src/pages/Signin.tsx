import instance from '@util/axios'
import { FormValues } from '@/models/signin'
import Form from '@components/signin/Form'
import { useCallback } from 'react'

function SigninPage() {
  const handleSubmit = useCallback(async (formValues: FormValues) => {
    try {
      const response = await instance.post('/api/login', formValues)
      console.log('로그인 성공:', response.data)
      // 로그인 성공 후 토큰 저장 및 리다이렉션 처리
    } catch (error) {
      console.error('로그인 실패:', error)
    }
  }, [])
  return (
    <div>
      <Form onSubmit={handleSubmit} />
    </div>
  )
}

export default SigninPage
