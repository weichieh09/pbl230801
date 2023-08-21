<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">管理功能</h3>
        <hr />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" to="/pbl/wcc801">
          <b-icon icon="GiftFill" />
          <br />
          <h5>獎勵</h5>
        </b-button>
      </b-col>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" to="/pbl/wcc701">
          <b-icon icon="download" />
          <br />
          <h5>下載</h5>
        </b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" to="/pbl/wcc601">
          <b-icon icon="Calendar3WeekFill" />
          <br />
          <h5>賽事</h5>
        </b-button>
      </b-col>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" to="/pbl/wcc401">
          <b-icon icon="FlagFill" />
          <br />
          <h5>球隊</h5>
        </b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6">
        <b-button
          block
          variant="outline-primary"
          size="lg"
          to="/admin/user-management"
          :disabled="!(hasAnyAuthority('ROLE_ADMIN') && authenticated)"
        >
          <b-icon icon="PeopleFill" />
          <br />
          <h5>用戶</h5>
        </b-button>
      </b-col>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" to="/account/password">
          <b-icon icon="ShieldLockFill" />
          <br />
          <h5>密碼</h5>
        </b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6">
        <b-button v-b-modal.about-modal block variant="outline-primary" size="lg">
          <b-icon icon="QuestionCircleFill" />
          <br />
          <h5>關於</h5>
        </b-button>
      </b-col>
      <b-col cols="6">
        <!-- <b-button block variant="outline-primary" size="lg" disabled>
          <b-icon icon="QuestionCircleFill" />
          <br />
          <h5>??</h5>
        </b-button> -->
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="outline-primary" size="lg" to="/">回首頁</b-button>
      </b-col>
    </b-row>
    <br />

    <!-- The modal -->
    <b-row>
      <b-col cols="12">
        <b-modal id="about-modal" title="關於">
          <b-row>
            <b-col cols="12" class="d-flex justify-content-center">羽球拍拍(pbl230801)</b-col>
            <b-col cols="12" class="d-flex justify-content-center">通用的羽球賽事管理系統</b-col>
            <b-col cols="12" class="d-flex justify-content-center">版本 230821F</b-col>
            <b-col cols="12" class="d-flex justify-content-center">Release date: 2023 Aug</b-col>
            <b-col cols="12" class="d-flex justify-content-center">眾飛科技 © 2023</b-col>
            <b-col cols="12" class="d-flex justify-content-center">Powered by MGDsn</b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./wcc301.ts"></script>
