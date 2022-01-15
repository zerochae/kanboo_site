<template>
  <div class="container">
    <h2>Gantt Chart</h2>
    <div class="chart-container">
      <div class="gantt-container">
        <table>
          
          <thead>

            <tr v-for="(item, index) in itemData"
                :key="index">
              <th v-if="index === 0">Date</th>
              <th v-if="index !== 0">{{item.title}}</th>
            </tr>
          </thead>

          <tbody>
            <tr class="date">
              <td v-for="item in ganttData.dateList"
                  :key="item">
                <div>{{item.substring(0, item.lastIndexOf("-"))}}</div>
              </td>
            </tr>
            <tr class="date">
              <td v-for="item in ganttData.dateList"
                  :key="item">
                <div>{{item.substring(item.indexOf("-") + 1, item.length)}}</div>
              </td>
            </tr>

            <tr v-for="(item, index) in itemData" :key="index">
              <td v-for="(gantt, gIndex) in ganttData.dateList" :key="gIndex">
                <span v-if="gIndex < item.frontEmpty"></span>
                <span v-else-if="gIndex >= item.frontEmpty + item.dateLength"></span>
                <span class="bar" v-else>1</span>
              </td>
            </tr>

          </tbody>

        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "gantt",
  data() {
    return {
      ganttData: {},
      itemData: []
    }
  },
  methods: {
    getDateList(start, end) {
      const sDay = new Date(start)
      const eDay = new Date(end).getTime()
      const gap = eDay - sDay
      const result = Math.floor(gap / (1000 * 60 * 60 * 24))
      
      const list = []
      
      for(let i = 1; i <= result; i++) {
        const t = new Date("2021-01-01")
        t.setDate(i)
        list.push(t.toLocaleDateString().replaceAll(". ", "-").replace(".", ""))
      }

      const data = {
        dateList: list,
        totalDay: result,
        todday: new Date().toLocaleDateString().replaceAll(". ", "-").replace(".", ""),
      }

      this.ganttData = data
      this.itemData = [
          {
            title: "UI design",
            start: "2021-1-1",
            end: "2021-1-5",
            frontEmpty: 0,
            dateLength: 5
          },
          {
            title: "UI design",
            start: "2021-1-4",
            end: "2021-1-10",
            frontEmpty: 3,
            dateLength: 7
          },
      ]
    }
  },
  mounted() {
    this.getDateList("2021-01-01", "2021-01-31")
  }
}
</script>

<style scoped>
.container {
  width: 70vw;
  height: 510px;
  background: #2C2F3B;
  padding: 10px;
  border-radius: 5px;
}

h2 {
  font-size: 32px;
  color: #ffffff;
  margin-bottom: 10px;
}

.chart-container {
  width: calc(100% - 100px);
  height: calc(100% - 52px);
  margin: 0 auto;
  overflow: scroll;
  position: relative;
}

table {
  display: flex;
}

thead {
  width: 20%;
  overflow: scroll;
}

thead tr {
  display: flex;
  justify-content: center;
  align-items: center;
}

thead tr th {
  font-size: 16px;
}

tbody {
  width: 80%;
  overflow: scroll;
}

.date {
  border-bottom: 1px solid #fff;
}

.date td div {
  width: 70px;
  text-align: center;
  color: #ffffff;
}

tr {
  height: 20px;
}

th {
  color: #ffffff;
}

td {
  width: 70px;
}

.bar {
  display: inline-block;
  width: 100%;
  height: 100%;
  background: #FFEF60;
  overflow: hidden;
  text-indent: -9999px;
}
</style>