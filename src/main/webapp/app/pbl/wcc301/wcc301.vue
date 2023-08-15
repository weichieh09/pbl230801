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
          <b-icon icon="PeopleFill" />
          <br />
          <h5>球隊</h5>
        </b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" disabled>
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
  </b-container>
</template>

<script lang="ts" src="./wcc301.ts"></script>
