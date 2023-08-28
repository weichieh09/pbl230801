<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">球隊管理</h3>
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="primary" @click="createTeam()" size="lg" :disabled="!(hasAnyAuthority('ROLE_ADMIN') && authenticated)"
          >新增球隊</b-button
        >
      </b-col>
    </b-row>
    <br />
    <b-row v-if="teams.length > 0">
      <b-col cols="12">
        <table class="table">
          <thead>
            <tr>
              <!-- <th style="width: 20%">序號</th> -->
              <th style="width: 70%">
                <h5>球隊名稱</h5>
              </th>
              <th style="width: 30%">
                <h5>操作</h5>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in teams" :key="index">
              <!-- <td class="align-middle">{{ index + 1 }}</td> -->
              <td class="align-middle">
                <h5>{{ item.teamNm }}</h5>
              </td>
              <td>
                <b-button
                  style="margin: 1px"
                  variant="info"
                  @click="editTeam(item)"
                  :hidden="!(hasAnyAuthority('ROLE_ADMIN') && authenticated)"
                  >編輯</b-button
                ><br :hidden="!(hasAnyAuthority('ROLE_ADMIN') && authenticated)" />
                <b-button
                  style="margin: 1px"
                  variant="danger"
                  @click="prepareRemoveTeam(item)"
                  :hidden="!(hasAnyAuthority('ROLE_ADMIN') && authenticated)"
                  >刪除</b-button
                ><br :hidden="!(hasAnyAuthority('ROLE_ADMIN') && authenticated)" />
                <b-button style="margin: 1px" variant="warning" @click="editPlayer(item)">球員</b-button>
                <br :hidden="hasAnyAuthority('ROLE_ADMIN') && authenticated" />
              </td>
            </tr>
          </tbody>
        </table>
      </b-col>
      <b-col cols="12">
        <b-pagination
          v-model="page.currentPage"
          :total-rows="page.objTotal"
          :per-page="page.perPage"
          @input="pageLoad(page.currentPage)"
          align="fill"
        />
      </b-col>
    </b-row>
    <b-row v-else>
      <b-col cols="12">
        <b-alert show variant="warning">目前還沒有球隊唷!</b-alert>
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
            <b-col cols="12"> 您確定要刪除此球隊嗎? </b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./wcc401.ts"></script>
