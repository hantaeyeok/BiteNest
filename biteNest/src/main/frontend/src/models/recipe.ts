export interface FormValues {
  title: string
  description: string
  time: number
  category: string
  ingredients: { name: string; quantity: string }[]
  subIngredients?: { name: string | null; quantity: string | null }[] | null
  sauce?: { name: string | null; quantity: string | null }[] | null
  steps: { description: string; image: File | null }[] // `undefined` 제거
  tip?: string
}
