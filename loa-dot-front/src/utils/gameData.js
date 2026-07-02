export const GRADE_COLORS = {
  '일반': '#ffffff',
  '고급': '#51ffffff',
  '희귀': '#11b5ffff',
  '영웅': '#ce43ffff',
  '전설': '#ff9900',
  '유물': '#ea6000',
  '고대': '#b380ff',
  '에스더': '#4afbe6'
}

export const getGradeColor = (grade) => GRADE_COLORS[grade] || '#94a3b8'