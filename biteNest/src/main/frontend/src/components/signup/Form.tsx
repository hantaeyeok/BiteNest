import { ChangeEvent, useCallback, useMemo, useState, MouseEvent } from 'react'
import Button from '@components/shared/Button'
import TextField from '@components/shared/TextField'

import { FormValues } from '@models/signup'
import validator from 'validator'
import Agreement from '@shared/Agreement'
import { 약관목록 } from '@constants/apply'

function validate(formValues: FormValues) {
  let errors: Partial<FormValues> = {}

  if (validator.isEmail(formValues.userId) === false) {
    errors.userId = '이메일형식을 확인해주세요'
  }

  if (formValues.password.length < 8) {
    errors.password = '비밀번호를 8자 이상 입력해주세요'
  }

  if (formValues.rePassword.length < 8) {
    errors.rePassword = '비밀번호를 8자 이상 입력해주세요'
  } else if (
    validator.equals(formValues.rePassword, formValues.password) === false
  ) {
    errors.rePassword = '비밀번호를 확인해주세요'
  }

  if (formValues.name.length < 2) {
    errors.name = '이름은 2글자 이상 입력해주세요'
  }
  if (formValues.nickname.length < 2) {
    errors.nickname = '닉네임은 2글자 이상 입력해주세요'
  }
  return errors
}

function Form({ onSubmit }: { onSubmit: (formValues: FormValues) => void }) {
  const [formValues, setFormValues] = useState<FormValues>({
    userId: '',
    password: '',
    rePassword: '',
    name: '',
    nickname: '',
    imageUrl: '',
    introduction: '',
    loginType: '1',
  })

  const [dirty, setDirty] = useState<Partial<FormValues>>({})

  const [termsAgreements, setTermsAgreements] = useState(() => {
    return 약관목록.reduce<Record<string, boolean>>(
      (prev, term) => ({
        ...prev,
        [term.id]: false,
      }),
      {},
    )
  })

  const errors = useMemo(() => validate(formValues), [formValues])

  const 모든약관이동의되었는가 = Object.values(termsAgreements).every(
    (동의여부) => 동의여부,
  )

  const 제출가능한상태인가 = Object.keys(errors).length === 0

  const handleFormValues = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setFormValues((prevFormValues) => ({
      ...prevFormValues,
      [e.target.name]: e.target.value,
    }))
  }, [])

  const handleBlur = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setDirty((prevDirty) => ({
      ...prevDirty,
      [e.target.name]: 'true',
    }))
  }, [])

  const handleAllAgreement = useCallback(
    (_: MouseEvent<HTMLElement>, checked: boolean) => {
      setTermsAgreements((prevTerms) => {
        const newTerms = { ...prevTerms } // 새로운 객체 생성
        Object.keys(newTerms).forEach((key) => {
          newTerms[key] = checked
        })
        return newTerms
      })
    },
    [],
  )
  return (
    <div className="sm:container sm:mx-auto py-6 px-6 sm:px-36">
      <p className="text-2xl my-6">회원가입</p>
      <TextField
        label="이메일"
        name="userId"
        placeholder="prumpy11@naver.com"
        value={formValues.userId}
        onChange={handleFormValues}
        hasError={Boolean(dirty.userId) && Boolean(errors.userId)}
        helpMessage={Boolean(dirty.userId) ? errors.userId : ''}
        onBlur={handleBlur}
      />
      <div className="my-6"></div>

      <TextField
        label="패스워드"
        name="password"
        type="password"
        value={formValues.password}
        onChange={handleFormValues}
        hasError={Boolean(dirty.password) && Boolean(errors.password)}
        helpMessage={Boolean(dirty.password) ? errors.password : ''}
        onBlur={handleBlur}
      />

      <TextField
        label="패스워드확인"
        name="rePassword"
        type="password"
        value={formValues.rePassword}
        onChange={handleFormValues}
        hasError={Boolean(dirty.rePassword) && Boolean(errors.rePassword)}
        helpMessage={Boolean(dirty.rePassword) ? errors.rePassword : ''}
        onBlur={handleBlur}
      />
      <div className="my-6"></div>
      <TextField
        label="이름"
        name="name"
        placeholder="홍길동"
        value={formValues.name}
        onChange={handleFormValues}
        hasError={Boolean(dirty.name) && Boolean(errors.name)}
        helpMessage={Boolean(dirty.name) ? errors.name : ''}
        onBlur={handleBlur}
      />
      <div className="my-6"></div>
      <TextField
        label="닉네임"
        name="nickname"
        placeholder="캬라멜"
        value={formValues.nickname}
        onChange={handleFormValues}
        hasError={Boolean(dirty.nickname) && Boolean(errors.nickname)}
        helpMessage={Boolean(dirty.nickname) ? errors.nickname : ''}
        onBlur={handleBlur}
      />
      <div className="my-6"></div>

      <Agreement>
        <Agreement.Title
          checked={모든약관이동의되었는가}
          onChange={handleAllAgreement}
        >
          약관에 모두 동의
        </Agreement.Title>
        {약관목록.map(({ id, title, link }) => (
          <Agreement.Description
            key={id}
            link={link}
            checked={termsAgreements[id]}
            onChange={(_, checked) => {
              setTermsAgreements((prevTerms) => ({
                ...prevTerms,
                [id]: checked, // 새로운 객체를 반환
              }))
            }}
          >
            {title}
          </Agreement.Description>
        ))}
      </Agreement>

      <div className="my-6"></div>
      <Button
        label="회원가입"
        disabled={!제출가능한상태인가 || !termsAgreements['01']}
        color="bg-brown-100"
        size="full"
        onClick={() => {
          onSubmit(formValues)
        }}
      ></Button>
    </div>
  )
}

export default Form
