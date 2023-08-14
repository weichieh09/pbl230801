<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">賽事管理</h3>
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="primary" @click="createEventZ()" size="lg">新增賽事</b-button>
      </b-col>
    </b-row>
    <br />
    <b-row v-if="eventZs.length > 0">
      <b-col cols="12">
        <table class="table">
          <thead>
            <tr>
              <!-- <th style="width: 20%">序號</th> -->
              <th style="width: 50%"><h5>賽事</h5></th>
              <th style="width: 20%"><h5>日期</h5></th>
              <th style="width: 30%"><h5>操作</h5></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in eventZs" :key="index">
              <!-- <td class="align-middle">{{ index + 1 }}</td> -->
              <td class="align-middle">
                <h5>{{ item.evntNm }}</h5>
                <h6 style="color: gray">{{ item.venue }}</h6>
              </td>
              <td class="align-middle">
                <h5>{{ getSimpleTime(item.evntDt).substr(0, 10) }}</h5>
              </td>
              <td>
                <b-button style="margin: 1px" variant="info" @click="editEventZ(item)">編輯</b-button><br />
                <b-button style="margin: 1px" variant="danger" @click="prepareRemoveEventZ(item)">刪除</b-button><br />
                <!-- <b-button style="margin: 1px" variant="warning" @click="editPlayer(item)" disabled>球隊</b-button> -->
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
        <b-alert show variant="warning">目前還沒有賽事唷!</b-alert>
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
        <b-modal ref="removeEventZ-modal" ok-title="刪除" ok-variant="danger" @ok="removeEventZ()" cancel-title="取消" title="刪除賽事">
          <b-row>
            <b-col cols="12"> 您確定要刪除此賽事嗎? </b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./wcc601.ts"></script>
