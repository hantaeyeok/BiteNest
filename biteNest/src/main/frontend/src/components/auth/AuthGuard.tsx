import { useEffect } from 'react'
import { useRecoilState } from 'recoil'
import { userAtom } from '@atoms/user'

function AuthGuard({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useRecoilState(userAtom)

  useEffect(() => {
    const token = localStorage.getItem('token')
    const email = localStorage.getItem('email')
    const nickName = localStorage.getItem('nickName')

    if (token && email && nickName) {
      // 토큰이 있으면 유저 정보를 Recoil로 설정
      setUser({
        token,
        email,
        nickName,
      })
    } else {
      // 유저 정보가 없으면 null로 설정
      setUser(null)
    }
  }, [setUser])

  // AuthGuard는 라우팅을 방해하지 않으므로 children을 그대로 반환
  return <>{children}</>
}

export default AuthGuard
