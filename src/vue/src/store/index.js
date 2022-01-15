import { createStore } from 'vuex'

import adminQnA from '@/store/modules/admin/adminQnA.js'
import userList from '@/store/modules/admin/userList.js'
import userProject from '@/store/modules/admin/userProject.js'

import global from '@/store/modules/common/global.js'
import member from '@/store/modules/common/member.js'
import menu from '@/store/modules/common/menu.js'
import style from '@/store/modules/common/style.js'

import dashBoard from '@/store/modules/main/dashBoard.js'
import erd from '@/store/modules/main/erd.js'
import gantt from '@/store/modules/main/gantt.js'
import git from '@/store/modules/main/git.js'
import htmlCompile from '@/store/modules/main/htmlCompile.js'
import issue from '@/store/modules/main/issue.js'
import javaCompile from '@/store/modules/main/javaCompile.js'
import kanban from '@/store/modules/main/kanban.js'
import projectBoard from '@/store/modules/main/projectBoard.js'
import projectList from '@/store/modules/main/projectList.js'
import demand from '@/store/modules/main/demand.js'
import scheduler from '@/store/modules/main/scheduler.js'
import setting from '@/store/modules/main/setting.js'
import view from '@/store/modules/main/view.js'
import socket from '@/store/modules/main/socket.js'

import community from '@/store/modules/noAccess/community.js'
import demo from '@/store/modules/noAccess/demo.js'
import home from '@/store/modules/noAccess/home.js'
import sign from '@/store/modules/noAccess/sign.js'
import userQnA from '@/store/modules/noAccess/userQnA.js'





const store = createStore({
  namespace: true,
  modules: {
    adminQnA,
    userList,
    userProject,
    global,
    member,
    menu,
    style,
    dashBoard: dashBoard,
    erd,
    gantt,
    git,
    htmlCompile,
    issue,
    javaCompile,
    kanban,
    projectBoard,
    projectList,
    demand,
    scheduler,
    setting,
    view,
    socket,
    community,
    demo,
    home,
    sign,
    userQnA
  }
})

export default store