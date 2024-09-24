import { useState, useEffect } from 'react'

import Button from '../shared/Button'
import RadioButton from '../shared/RadioButton'
import RecipeCategorySelect from './RecipeCategory'
import { FormValues } from '@models/recipe'
import IngredientsSection from './IngredientSectionForm'
import SubIngredientsSection from './SubIngredientSectionForm'
import SauceSection from './SauceSectionForm'
import StepsSection from './StepsSectionForm'

function Form() {
  const [formValues, setFormValues] = useState<FormValues>({
    title: '',
    description: '',
    time: 0,
    category: '', // 초기 카테고리 값
    ingredients: [{ name: '', quantity: '' }],
    subIngredients: null,
    sauce: null,
    steps: [{ description: '', image: null }], // index 제거
  })

  const [imagePreviews, setImagePreviews] = useState<string[]>([])

  const handleAddIngredient = () => {
    setFormValues((prev) => ({
      ...prev,
      ingredients: [...prev.ingredients, { name: '', quantity: '' }],
    }))
  }
  const handleRemoveIngredient = (index: number) => {
    const newIngredients = formValues.ingredients.filter((_, i) => i !== index)
    setFormValues({ ...formValues, ingredients: newIngredients })
  }

  const handleIngredientChange = (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => {
    const newIngredients = [...formValues.ingredients]
    newIngredients[index][field] = value
    setFormValues((prev) => ({ ...prev, ingredients: newIngredients }))
  }

  const handleAddSubIngredient = () => {
    if (!formValues.subIngredients) {
      // subIngredients가 처음 추가될 때 빈 배열로 초기화
      setFormValues((prev) => ({
        ...prev,
        subIngredients: [{ name: '', quantity: '' }],
      }))
    }
  }
  const handleRemoveSubIngredient = (index: number) => {
    const newSubIngredients = (formValues.subIngredients || []).filter(
      (_, i) => i !== index,
    )
    setFormValues({ ...formValues, subIngredients: newSubIngredients })
  }

  const handleSubIngredientChange = (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => {
    const newSubIngredients = [...(formValues.subIngredients || [])]
    newSubIngredients[index][field] = value
    setFormValues((prev) => ({ ...prev, subIngredients: newSubIngredients }))
  }

  const handleAddSauce = () => {
    if (!formValues.sauce) {
      // sauce가 처음 추가될 때 빈 배열로 초기화
      setFormValues((prev) => ({
        ...prev,
        sauce: [{ name: '', quantity: '' }],
      }))
    }
  }
  const handleRemoveSauce = (index: number) => {
    const newSauce = (formValues.sauce || []).filter((_, i) => i !== index)
    setFormValues({ ...formValues, sauce: newSauce })
  }

  const handleSauceChange = (
    index: number,
    field: 'name' | 'quantity',
    value: string,
  ) => {
    const newSauce = [...(formValues.sauce || [])]
    newSauce[index][field] = value
    setFormValues((prev) => ({ ...prev, sauce: newSauce }))
  }

  const handleAddStep = () => {
    setFormValues((prev) => ({
      ...prev,
      steps: [...prev.steps, { description: '', image: null }],
    }))
    setImagePreviews((prev) => [...prev, '']) // 미리보기 추가
  }

  const handleRemoveStep = (index: number) => {
    const newSteps = formValues.steps.filter((_, i) => i !== index)
    const newPreviews = imagePreviews.filter((_, i) => i !== index) // 미리보기도 삭제
    setFormValues((prev) => ({
      ...prev,
      steps: newSteps,
    }))
    setImagePreviews(newPreviews)
  }

  const handleStepChange = (index: number, value: string) => {
    const newSteps = [...formValues.steps]
    newSteps[index].description = value // description 업데이트
    setFormValues((prev) => ({ ...prev, steps: newSteps }))
  }

  const handleImageChange = (index: number, value: File | null) => {
    const newSteps = [...formValues.steps]
    newSteps[index].image = value // image 업데이트
    setFormValues((prev) => ({ ...prev, steps: newSteps }))
    // 이미지 미리보기 생성
    if (value) {
      const previewUrl = URL.createObjectURL(value)
      const newPreviews = [...imagePreviews]
      newPreviews[index] = previewUrl // 미리보기 URL 업데이트
      setImagePreviews(newPreviews)
    } else {
      const newPreviews = [...imagePreviews]
      newPreviews[index] = '' // 이미지가 없는 경우 빈 문자열로 설정
      setImagePreviews(newPreviews)
    }

    setFormValues((prev) => ({ ...prev, steps: newSteps }))
  }

  return (
    <div className="sm:container py-6 px-6 sm:px-36 text-brown-300 min-w-[300px] ">
      <div className="flex justify-center items-center justify-between space-x-4 ">
        <IconLeft />
        <div className="min-w-24">
          <p className="font-bold">레시피 작성</p>
          <p className="text-xs">작성중인 레시피</p>
        </div>
        <Button label="완료" size="medium" color="bg-brown-100"></Button>
      </div>
      <div className="bg-cream h-48">이미지 미리보기</div>
      <div className="my-6"></div>
      <div>
        <input
          type="text"
          className="mt-1 p-2 border border-brown-100 w-full rounded-md focus:outline-none focus:border-brown-300 focus:ring-brown-300"
          placeholder="레시피 이름을 알려주세요 (20자)"
          name="title"
          required
        />
        <div className="my-4"></div>
        <textarea
          className="mt-1 p-2 border border-brown-100 w-full rounded-md h-48 resize-none focus:outline-none focus:border-brown-300 focus:ring-brown-300"
          required
          placeholder="레시피의 간단한 설명을 만들어주세요 (150자)"
        />
        <div className="my-6"></div>
        <div>
          <label className="block text-lg font-bold text-brown-300">
            예상 소요시간*
          </label>
          <div className="flex space-x-4 mt-2">
            <RadioButton label="15분 미만" name="time" value={10} />
            <RadioButton label="15분컷" name="time" value={15} />
            <RadioButton label="30분컷" name="time" value={30} />
            <RadioButton label="45분 이상" name="time" value={45} />
          </div>
        </div>
        <div className="my-6"></div>
        <div>
          <label className="block text-lg font-bold text-brown-300">
            카테고리*
          </label>
          <RecipeCategorySelect />
        </div>
        <div className="my-6"></div>
        <IngredientsSection
          ingredients={formValues.ingredients}
          handleAddIngredient={handleAddIngredient}
          handleIngredientChange={handleIngredientChange}
          handleRemoveIngredient={handleRemoveIngredient}
        />
        <SubIngredientsSection
          subIngredients={formValues.subIngredients}
          handleAddSubIngredient={handleAddSubIngredient}
          handleSubIngredientChange={handleSubIngredientChange}
          handleRemoveSubIngredient={handleRemoveSubIngredient}
        />
        <SauceSection
          sauce={formValues.sauce}
          handleAddSauce={handleAddSauce}
          handleSauceChange={handleSauceChange}
          handleRemoveSauce={handleRemoveSauce}
        />
        <StepsSection
          steps={formValues.steps}
          handleAddStep={handleAddStep}
          handleStepChange={handleStepChange}
          handleRemoveStep={handleRemoveStep}
          handleImageChange={handleImageChange}
          imagePreviews={imagePreviews}
        />
      </div>
    </div>
  )
}

function IconLeft() {
  return (
    <svg
      className="feather feather-chevron-left"
      fill="none"
      height="24"
      stroke="currentColor"
      stroke-linecap="round"
      stroke-linejoin="round"
      stroke-width="2"
      viewBox="0 0 24 24"
      width="24"
      xmlns="http://www.w3.org/2000/svg"
    >
      <polyline points="15 18 9 12 15 6" />
    </svg>
  )
}

export default Form
