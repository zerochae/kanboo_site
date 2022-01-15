<template>
  <vue-cal
      xsmall
      locale="ko"
      :time="false"
      hide-view-selector
      @cell-focus="setSelectDate($event)"
      active-view="month"
      :disable-views="['week', 'day']"
      :events=$store.state.scheduler.data
      class="vuecal--blue-theme vuecal--rounded-theme "
      style="width: 100% ;height: 280px">
  </vue-cal>
  <!-- @click-focus="isCome($event)" -->
  <!-- @cell-focus="$store.state.scheduler.commit('sendselectDate', $event)" -->

</template>

<script>
import VueCal from 'vue-cal'
import { mapActions, mapMutations } from 'vuex'

export default {
  components : {
    VueCal,
  },
  data() {
    return {
      showAllDayEvents: 0,
      shortEventsOnMonthView: false,
      showEventCreationDialog: false,
      changeTheme : false,
      changeLang : false,
      // modal https://kr.vuejs.org/v2/examples/modal.html
    }
  },
  methods : {
    ...mapMutations({
      setSelectDate : 'scheduler/setSelectDate',
    }),
    ...mapActions({

    }),
  },
  mounted() {
  },
}
</script>

<style scope>
.vuecal--rounded-theme .vuecal__cell-content{
  font-size: 14px;
  width : 30px;
  height: 30px;
}
</style>