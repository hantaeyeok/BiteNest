import { useEffect, useState } from 'react'
import { useRecoilState } from 'recoil'
import { userAtom } from '@atoms/user'

function AuthGuard({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useRecoilState(userAtom)
  const [initialize, setInitialize] = useState(false) // 초기화 상태 추가

  useEffect(() => {
    const token = localStorage.getItem('token')
    const email = localStorage.getItem('email')
    const nickName = localStorage.getItem('nickName')
    const id = localStorage.getItem('id')

    if (token && email && nickName && id) {
      // 로컬 스토리지에 유저 정보가 있으면 설정
      setUser({
        token,
        email,
        nickName,
        id,
      })
    } else {
      // 유저 정보가 없으면 null로 설정
      setUser(null)
    }

    // 초기화 완료
    setInitialize(true) // 로딩이 완료되면 초기화 완료 상태로 변경
  }, [setUser])

  if (initialize === false) {
    // 초기화 중일 때 로딩 메시지 또는 스피너 출력
    return <div>Loading...</div>
  }

  // 초기화가 완료되고 유저가 있으면 자식 컴포넌트 렌더링
  return <>{children}</>
}

export default AuthGuard
