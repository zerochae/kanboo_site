create table role (
                      role_idx bigint not null auto_increment,
                      role_nm varchar(100),
                      primary key (role_idx)
);

create table view (
                      view_idx bigint auto_increment,
                      view_nm varchar(300),
                      view_url varchar(300),
                      primary key (view_idx)
);

create table view_role (
                           role_idx bigint not null,
                           view_idx bigint not null,
                           primary key (role_idx, view_idx),
                           foreign key (role_idx) references role(role_idx),
                           foreign key (view_idx) references view(view_idx)
);

create table master_code (
                             master_code_idx varchar(50),
                             master_code_en varchar(100),
                             master_code_ko varchar(100),
                             primary key (master_code_idx)
);

create table code_detail (
                             code_detail_idx varchar(50) not null,
                             code_detail_en varchar(50),
                             code_detail_ko varchar(150),
                             master_code_idx varchar(50),
                             primary key (code_detail_idx),
                             foreign key (master_code_idx) references master_code(master_code_idx)
);

create table member (
                        mem_idx bigint auto_increment,
                        mem_cel_num varchar(13),
                        mem_id varchar(50),
                        mem_img varchar(300),
                        mem_nick varchar(50),
                        mem_pass varchar(512),
                        mem_tag varchar(10),
                        mem_token varchar(50),
                        role_idx bigint,
                        primary key (mem_idx),
                        foreign key (role_idx) references role(role_idx)
);

create table project (
                         prjct_idx bigint auto_increment,
                         prjct_del_at char(1),
                         prjct_end_date timestamp,
                         prjct_nm varchar(300),
                         prjct_start_date timestamp,
                         prjct_progress integer,
                         prjct_compl_at char(1),
                         primary key (prjct_idx)
);

create table compiler (
                          com_idx bigint auto_increment,
                          com_nm varchar(300),
                          com_se char(1),
                          prjct_idx bigint,
                          primary key (com_idx),
                          foreign key (prjct_idx) references project(prjct_idx)
);

create table chat (
                      prjct_idx bigint not null,
                      mem_idx bigint not null,
                      primary key (mem_idx, prjct_idx),
                      foreign key (prjct_idx) references project(prjct_idx),
                      foreign key (mem_idx) references member(mem_idx)
);

create table board (
                       board_idx bigint auto_increment,
                       board_cn text,
                       board_date timestamp,
                       del_at char(1),
                       file_at char(1),
                       code_detail_idx varchar(50),
                       mem_idx bigint,
                       primary key (board_idx),
                       foreign key (code_detail_idx) references code_detail(code_detail_idx),
                       foreign key (mem_idx) references member(mem_idx)
);

create table answer (
                        answer_idx bigint auto_increment,
                        answer_cn text,
                        answer_date timestamp,
                        answer_del_at char(1),
                        board_idx bigint,
                        mem_idx bigint,
                        primary key (answer_idx),
                        foreign key (board_idx) references board(board_idx),
                        foreign key (mem_idx) references member(mem_idx)
);

create table calendar (
                          cal_idx bigint auto_increment,
                          cal_cn text,
                          cal_color varchar(50),
                          cal_del_at char(1),
                          cal_del_resn text,
                          cal_end_date timestamp,
                          cal_start_date timestamp,
                          cal_title varchar(300),
                          code_detail_idx varchar(50),
                          mem_idx bigint,
                          prjct_idx bigint,
                          primary key (cal_idx),
                          foreign key (code_detail_idx) references code_detail(code_detail_idx),
                          foreign key (mem_idx) references member(mem_idx),
                          foreign key (prjct_idx) references project(prjct_idx)
);

create table demand (
                        demand_idx bigint auto_increment,
                        demand_revise_date timestamp,
                        prjct_idx bigint,
                        primary key (demand_idx),
                        foreign key (prjct_idx) references project(prjct_idx)
);

create table gantt (
                       gt_idx bigint auto_increment,
                       gt_end_date timestamp,
                       gt_explanation text,
                       gt_priority varchar(50),
                       gt_progress integer,
                       gt_start_date timestamp,
                       gt_state char(1),
                       gt_title varchar(300),
                       prjct_idx bigint,
                       mem_idx bigint,
                       primary key (gt_idx),
                       foreign key (prjct_idx) references project(prjct_idx),
                       foreign key (mem_idx) references member(mem_idx)
);

create table kanban (
                        kb_idx bigint auto_increment,
                        prjct_idx bigint,
                        primary key (kb_idx),
                        foreign key (prjct_idx) references project(prjct_idx)
);

create table answer_report (
                               answer_report_idx bigint auto_increment,
                               answer_report_resn text,
                               answer_idx bigint,
                               mem_idx bigint,
                               primary key (answer_report_idx),
                               foreign key (answer_idx) references answer(answer_idx),
                               foreign key (mem_idx) references member(mem_idx)
);

create table ban (
                     ban_idx bigint auto_increment,
                     ban_end_date timestamp,
                     ban_start_date timestamp,
                     mem_idx bigint,
                     primary key (ban_idx),
                     foreign key (mem_idx) references member(mem_idx)
);

create table board_report (
                              board_report_idx bigint auto_increment,
                              board_report_resn text,
                              board_idx bigint,
                              mem_idx bigint,
                              primary key (board_report_idx),
                              foreign key (board_idx) references board(board_idx),
                              foreign key (mem_idx) references member(mem_idx)
);

create table calendar_check (
                                cal_chk_idx bigint auto_increment,
                                cal_idx bigint,
                                mem_idx bigint,
                                primary key (cal_chk_idx),
                                foreign key (cal_idx) references calendar(cal_idx),
                                foreign key (mem_idx) references member(mem_idx)
);

create table chatting_content (
                                  chat_content_idx bigint auto_increment,
                                  chat_cn text,
                                  chat_cn_date timestamp,
                                  mem_idx bigint,
                                  prjct_idx bigint,
                                  primary key (chat_content_idx),
                                  foreign key (mem_idx, prjct_idx) references chat(mem_idx, prjct_idx)
);

create table compiler_file (
                               com_file_idx bigint auto_increment,
                               com_file_cn text,
                               com_idx bigint,
                               primary key (com_file_idx),
                               foreign key (com_idx) references compiler(com_idx)
);

create table demand_content (
                                demand_cn_idx bigint auto_increment,
                                demand_cn_detail text,
                                demand_cn_id text,
                                demand_cn_nm text,
                                demand_cn_num text,
                                demand_cn_requst_nm text,
                                demand_cn_rm text,
                                demand_cn_se text,
                                demand_idx bigint,
                                primary key (demand_cn_idx),
                                foreign key (demand_idx) references demand(demand_idx)
);

create table file (
                      file_idx bigint auto_increment,
                      extension_name varchar(50),
                      file_name varchar(300),
                      file_path varchar(300),
                      file_size varchar(100),
                      board_idx bigint,
                      primary key (file_idx),
                      foreign key (board_idx) references board(board_idx)
);

create table git (
                     prjct_idx bigint auto_increment,
                     git_repo varchar(500),
                     primary key (prjct_idx),
                     foreign key (prjct_idx) references project(prjct_idx)
);

create table issue (
                       issue_idx bigint auto_increment,
                       issue_cn text,
                       issue_date timestamp,
                       issue_state varchar(10),
                       issue_git_file varchar(300),
                       mem_idx bigint,
                       prjct_idx bigint,
                       primary key (issue_idx),
                       foreign key (mem_idx) references member(mem_idx),
                       foreign key (prjct_idx) references project(prjct_idx)
);

create table kanban_item (
                             kb_itm_idx bigint auto_increment,
                             kb_badge varchar(300),
                             kb_cn text,
                             kb_color varchar(10),
                             kb_date timestamp,
                             kb_itm_num varchar(100),
                             kb_start_date timestamp,
                             kb_end_date timestamp,
                             kb_idx bigint,
                             mem_idx bigint,
                             primary key (kb_itm_idx),
                             foreign key (kb_idx) references kanban(kb_idx),
                             foreign key (mem_idx) references member(mem_idx)
);

create table likes (
                       like_idx bigint auto_increment,
                       board_idx bigint,
                       mem_idx bigint,
                       primary key (like_idx),
                       foreign key (board_idx) references board(board_idx),
                       foreign key (mem_idx) references member(mem_idx)
);

create table notification (
                              ntcn_idx bigint auto_increment,
                              ntcn_at char(1),
                              ntcn_cn text,
                              ntcn_date timestamp,
                              code_detail_idx varchar(50),
                              mem_idx bigint,
                              prjct_idx bigint,
                              primary key (ntcn_idx),
                              foreign key (code_detail_idx) references code_detail(code_detail_idx),
                              foreign key (mem_idx) references member(mem_idx),
                              foreign key (prjct_idx) references project(prjct_idx)
);

create table project_board (
                               board_idx bigint not null,
                               prjct_idx bigint not null,
                               primary key (board_idx, prjct_idx),
                               foreign key (board_idx) references board(board_idx),
                               foreign key (prjct_idx) references project(prjct_idx)
);

create table project_member (
                                mem_idx bigint,
                                prjct_idx bigint,
                                prjct_mem_role varchar(10),
                                primary key (mem_idx, prjct_idx),
                                foreign key (mem_idx) references member(mem_idx),
                                foreign key (prjct_idx) references project(prjct_idx)
);

create table erd (
                     erd_idx bigint auto_increment,
                     prjct_idx bigint,
                     erd_name varchar(300),
                     erd_order bigint,
                     primary key (erd_idx),
                     foreign key (prjct_idx) references project(prjct_idx)
);

create table erd_column (
                            erd_column_idx bigint auto_increment,
                            erd_idx bigint,
                            erd_column_name varchar(300),
                            erd_column_type varchar(100),
                            erd_column_constraint varchar(100),
                            erd_column_references varchar(300),
                            primary key (erd_column_idx),
                            foreign key (erd_idx) references erd(erd_idx)
);

create table board_gantt (
                             gt_idx bigint,
                             board_idx bigint,
                             prjct_idx bigint,
                             primary key (gt_idx, board_idx, prjct_idx),
                             foreign key (gt_idx) references gantt(gt_idx),
                             foreign key (board_idx, prjct_idx) references project_board(board_idx, prjct_idx)
);

