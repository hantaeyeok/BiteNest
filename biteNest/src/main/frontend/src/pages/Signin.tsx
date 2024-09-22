import TextField from '@/components/shared/TextField'

function SigninPage() {
  return (
    <div className="sm:container sm:mx-auto py-6 px-6 sm:px-36">
      <p className="text-2xl my-6">로그인</p>
      <TextField
        label="이메일"
        placeholder="prumpy11@naver.com"
        helpMessage="이메일형식에 맞춰 입력해주세요"
      />
      <TextField
        label="패스워드"
        type="password"
        helpMessage="이메일형식에 맞춰 입력해주세요"
      />
    </div>
  )
}

export default SigninPage
