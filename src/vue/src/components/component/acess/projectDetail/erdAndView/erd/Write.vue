<template>
  <div class="add-container">
    <div class="wrap">
      <button class="add-btn"
              @click="openAndClose">{{this.$store.state.erd.writeData.popupName}}</button>
      <div class="add-box"
            v-if="this.$store.state.erd.writeData.isOpen">

        <div class="modal-top">
          <input v-model="this.$store.state.erd.writeData.tableData.name" type="text" placeholder="table name">
          <div class="add-col-wrap">
            <button @click="addCol" class="add-col">+</button>
            <button @click="delCol" class="del-col">-</button>
          </div>
        </div>

        <ul class="list">
          <li class="list-header">
            <span>name</span>
            <span>type</span>
            <span>constraint</span>
            <span>references</span>
          </li>

          <li class="list-body"
              v-for="item in this.$store.state.erd.writeData.tableData.columns"
              :key="item">
            <input v-model="item.name" type="text" placeholder="name"/>
            <input v-model="item.type" type="text" placeholder="type"/>

            <select v-model="item.constraint">
              <option>null</option>
              <option>pk</option>
              <option>fk</option>
            </select>

            <select v-model="item.references">
              <option>null</option>
              <option v-for="item in this.$store.state.erd.relation['primaryKey']" :key="item">{{item[0].table}}</option>
            </select>
          </li>
        </ul>

        <button @click="create"
                class="add-table-btn"
                type="button">create table</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
export default {
  name: "table",
  data() {
    return {

    }
  },
  methods: {
    ...mapMutations({
      openAndClose: 'erd/openAndClose',
      addCol: 'erd/addCol',
      delCol: 'erd/delCol',
      createTable: 'erd/createTable',
      setRelation: 'erd/setRelation'
    }),
    create() {
      this.createTable()
      this.setRelation()
    }
  }
}
</script>

<style scoped>
button {
  color: #fff;
}

input {
  outline: none;
  color: #fff;
}

.add-btn {
  margin-bottom: 10px;
  color: #999;
  transition: color .1s ease-in;
}

.add-btn:hover {
  color: #fff;
}

.add-btn,
.add-table-btn {
  margin-left: auto;
  display: block;
}

.add-box {
  background: #2C2F3B;
  color: #fff;
  padding: 5px 10px;
  width: fit-content;
  border-radius: 5px;
  border: 1px solid #fff;
}

.modal-top {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.modal-top input {
  border: none;
  padding: 5px;
  background: none;
  border-bottom: 1px solid #fff;
}

.list {
  margin-bottom: 10px;
}

.list-header,
.list-body {
  display: flex;
  justify-content: space-between;
}

.list-header {
  padding-bottom: 5px;
  margin-bottom: 5px;
}

.list-header span {
  color: #999;
}

.list-header span,
.list-body input {
  width: 100px;
  border: none;
  background: none;
}
</style>