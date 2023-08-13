<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">球隊管理</h3>
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="primary" @click="createTeam()" size="lg">新增球隊</b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12">
        <table class="table">
          <thead>
            <tr>
              <th style="width: 20%">序號</th>
              <th style="width: 50%">球隊名稱</th>
              <th style="width: 30%">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in teams" :key="index">
              <td class="align-middle">{{ index + 1 }}</td>
              <td class="align-middle">{{ item.teamNm }}</td>
              <td>
                <b-button style="margin: 1px" variant="info" @click="editTeam(item)">編輯</b-button><br />
                <b-button style="margin: 1px" variant="danger" @click="prepareRemoveTeam(item)">刪除</b-button><br />
                <b-button style="margin: 1px" variant="warning" @click="editPlayer(item)" disabled>球員</b-button>
              </td>
            </tr>
          </tbody>
        </table>
      </b-col>
      <b-col cols="12" class="d-flex justify-content-center">
        <b-pagination v-model="page.currentPage" :total-rows="page.objTotal" :per-page="page.perPage" @input="pageLoad(page.currentPage)" />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="outline-primary" size="lg" @click="$router.go(-1)">返回</b-button>
      </b-col>
    </b-row>
    <br />

    <!-- The modal -->
    <b-row>
      <b-col cols="12">
        <b-modal ref="removeTeam-modal" ok-title="刪除" ok-variant="danger" @ok="removeTeam()" cancel-title="取消" title="刪除球隊">
          <b-row>
            <b-col cols="12"> 您確定要刪除球隊嗎? </b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./p4.ts"></script>
