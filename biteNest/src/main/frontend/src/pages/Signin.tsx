import instance from '@util/axios'
import { FormValues } from '@/models/signin'
import Form from '@components/signin/Form'
import { useCallback } from 'react'
import { useNavigate } from 'react-router-dom'
import useUser from '@hooks/auth/useUser'

function SigninPage() {
  const navigate = useNavigate() // 리다이렉션을 위한 훅
  const { setUser } = useUser()

  const handleSubmit = useCallback(
    async (formValues: FormValues) => {
      try {
        const response = await instance.post(
          'https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/login',
          formValues,
        )
        console.log('로그인 성공:', response.data)

        // 토큰을 localStorage에 저장
        const { token, email, nickName } = response.data
        localStorage.setItem('token', token)
        localStorage.setItem('email', email)
        localStorage.setItem('nickName', nickName)

        setUser({ email, nickName, token })

        // 로그인 성공 후 리다이렉션 (예: 대시보드로 이동)
        navigate('/') // 원하는 경로로 리다이렉션
      } catch (error) {
        console.error('로그인 실패:', error)
        alert('로그인 실패. 아이디 또는 비밀번호를 확인해주세요')
      }
    },
    [navigate, setUser],
  )

  return (
    <div>
      <Form onSubmit={handleSubmit} />
    </div>
  )
}

export default SigninPage
