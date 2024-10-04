import { useCallback } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import instance from '@util/axios'
import { FormValues } from '@/models/signin'
import Form from '@components/signin/Form'
import useUser from '@hooks/auth/useUser'

function SigninPage() {
  const navigate = useNavigate()
  const location = useLocation()
  const { setUser } = useUser()

  // 사용자가 로그인 전에 접근하려던 페이지 (state로부터 가져옴)
  const from = location.state?.from?.pathname || '/'

  const handleSubmit = useCallback(
    async (formValues: FormValues) => {
      console.log('formvalue', formValues)
      try {
        const response = await instance.post(
          'https://3ccfb3c6-7a46-4902-b117-a23e940861d2.mock.pstmn.io/api/login',
          formValues,
        )
        console.log('로그인 성공:', response.data)

        // 토큰을 localStorage에 저장
        const { token, email, nickName, id } = response.data
        localStorage.setItem('token', token)
        localStorage.setItem('email', email)
        localStorage.setItem('nickName', nickName)
        localStorage.setItem('id', id)

        setUser({ email, nickName, token, id })

        // 로그인 후 사용자가 가려고 했던 경로로 리다이렉션
        navigate(from, { replace: true })
      } catch (error) {
        console.error('로그인 실패:', error)
        alert('로그인 실패. 아이디 또는 비밀번호를 확인해주세요')
      }
    },
    [navigate, from, setUser], // from 경로도 의존성에 추가
  )

  return (
    <div>
      <Form onSubmit={handleSubmit} />
    </div>
  )
}

export default SigninPage
