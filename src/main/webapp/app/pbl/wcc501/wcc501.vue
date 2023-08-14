<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">球員管理</h3>
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="primary" @click="createPlyr()" size="lg">新增球員</b-button>
      </b-col>
    </b-row>
    <br />
    <b-row v-if="plyrs.length > 0">
      <b-col cols="12">
        <table class="table">
          <thead>
            <tr>
              <!-- <th style="width: 20%">序號</th> -->
              <th style="width: 20%"><h5>等級</h5></th>
              <th style="width: 50%"><h5>姓名</h5></th>
              <th style="width: 30%"><h5>操作</h5></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in plyrs" :key="index">
              <!-- <td class="align-middle">{{ index + 1 }}</td> -->
              <td class="align-middle">
                <h5>{{ item.plyrLvl }}</h5>
              </td>
              <td class="align-middle">
                <h5>{{ item.plyrNm }}</h5>
              </td>
              <td>
                <b-button style="margin: 1px" variant="info" @click="editPlyr(item)">編輯</b-button><br />
                <b-button style="margin: 1px" variant="danger" @click="prepareRemovePlyr(item)">刪除</b-button><br />
              </td>
            </tr>
          </tbody>
        </table>
      </b-col>
      <b-col cols="12" class="d-flex justify-content-center">
        <b-pagination v-model="page.currentPage" :total-rows="page.objTotal" :per-page="page.perPage" @input="pageLoad(page.currentPage)" />
      </b-col>
    </b-row>
    <b-row v-else>
      <b-col cols="12">
        <b-alert show variant="warning">這支隊伍還沒有球員唷!</b-alert>
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
        <b-modal ref="removePlyr-modal" ok-title="刪除" ok-variant="danger" @ok="removePlyr()" cancel-title="取消" title="刪除球員">
          <b-row>
            <b-col cols="12"> 您確定要刪除此球員嗎? </b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./wcc501.ts"></script>
