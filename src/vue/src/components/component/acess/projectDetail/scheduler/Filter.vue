<template>
  <h1>Filter</h1>

  <ul>
    <li v-for="item in filters" :key="item" >
      <input v-if="item.isClick === true" checked type="checkbox" :id="item.filterName" @click="checkBoxClick(item)">
      <input v-else-if="item.isClick === false" type="checkbox" :id="item.filterName" @click="checkBoxClick(item)">
      <label :for="item.filterName">{{item.koName}}</label>
    </li>
  </ul>
</template>

<script>
import { mapMutations, mapState } from 'vuex'

export default {
  computed : {
    ...mapState({
      showAllDayEvents : state => state.scheduler.showAllDayEvents
    })
  },
  data() {
    return {
      filters : [
        {
          filterName : 'all',
          koName : '전체',
          isClick: true
        },
        {
          filterName : 'common',
          koName : '공통',
          isClick: false
        },
        {
          filterName : 'individual',
          koName : '개인',
          isClick: false
        },
        {
          filterName : 'notice',
          koName : '공지',
          isClick: false
        },
        {
          filterName : 'emergency',
          koName : '긴급',
          isClick: false
        },
        {
          filterName : 'vacation',
          koName : '휴가',
          isClick: false
        },
        {
          filterName : 'note',
          koName : '기타',
          isClick: false
        },
      ]
    }
  },
  methods : {
    ...mapMutations({
      setFilterValue : 'scheduler/setFilterValue',
      setDataToSecondData : 'scheduler/setDataToSecondData',
      toggleAllDayContent : 'scheduler/toggleAllDayContent',
    }),

    checkBoxClick(item) {
      this.$store.state.scheduler.showAllDayEvents = 0
      this.$store.state.scheduler.data = this.$store.state.scheduler.secondData

      if(item.isClick) {
        item.isClick = false
        this.filters[0].isClick = true
        // 전체로 checked 되니 전체 데이터를 뿌려주는 함수 호출해야함
        this.setDataToSecondData()
        return
      }

      this.filters.forEach(i => {
        i.isClick = false
      })
      item.isClick = true

      this.setFilterValue(item.filterName)
    },

  },
  watch : {
    selectedFilterName(){
      this.selectedCheckbox()
    }
  }
}
</script>

<style>

</style>