export interface FormValues {
  title: string
  description: string
  titleImage: File | null
  time: number
  category: string
  ingredients: { name: string; quantity: string }[]
  subIngredients?: { name: string | null; quantity: string | null }[] | null
  sauce?: { name: string | null; quantity: string | null }[] | null
  steps: { stepOrder: number; description: string; image: File | null }[]
  tips?: string[] | null
}
