import { useRecoilValue, useSetRecoilState } from 'recoil'
import { useEffect } from 'react'
import { userAtom } from '@atoms/user'

function useUser() {
  const user = useRecoilValue(userAtom)
  const setUser = useSetRecoilState(userAtom)

  useEffect(() => {
    // localStorage에서 유저 정보 가져오기
    const token = localStorage.getItem('token')
    const email = localStorage.getItem('email')
    const nickName = localStorage.getItem('nickName')
    const id = localStorage.getItem('id')

    // 유저 정보가 있으면 Recoil 상태에 설정
    if (token && email && nickName && id) {
      setUser({ token, email, nickName, id })
    }
  }, [setUser])

  return { user, setUser }
}

export default useUser
