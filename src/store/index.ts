// 这段代码整体上为Vue应用提供了一个简单的计数器功能，能够响应数据变化并进行相应的视图更新。
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
// - ref
// - 作用：用于创建一个响应式的数据对象。
// - 用法： ref 返回一个包含 value 属性的对象，任何对 value 的修改都会触发相关的视图更新。
// - 示例： const count = ref(0); 创建一个初始值为0的响应式变量 count 。
// - computed
// - 作用：用于创建一个计算属性，计算属性会根据其依赖的响应式数据自动更新。
// - 用法： computed 接收一个函数，返回一个响应式的计算结果。
export const useCounterStore = defineStore('counter', () => {
  const count = ref<number>(0) // 使用TypeScript类型注解
  const doubleCount = computed((): number => count.value * 2) // 使用类型注解
  function increment(): void { // 指定函数返回类型
    count.value++
  }
  return { count, doubleCount, increment }
//   返回 count 、 doubleCount 和 increment ，使得这些状态和方法可以在Vue组件中使用。
})