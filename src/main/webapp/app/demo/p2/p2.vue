<template>
  <b-container fluid>
    <b-row>
      <b-col cols="12">
        <br />
        <b-input-group size="lg" prepend="球隊">
          <b-form-select v-model="form.team" :options="teams" v-on:change="teamChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="日期">
          <b-form-input type="date" v-model="form.date" v-on:change="dateChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="場地">
          <b-form-select v-model="form.space" :options="spaces" v-on:change="spaceChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="賽事">
          <b-form-input v-model="form.event" disabled />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <br />
      <b-col cols="12" class="d-flex justify-content-center">
        <h3 class="text-success">勝方</h3>
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-button block variant="outline-success" size="lg" @click="showModal('wPlyr1')">{{ resultForm.wPlyr1 }}</b-button>
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-form-input type="number" size="lg" class="form-control-success" v-model="resultForm.wScr" />
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-button block variant="outline-success" size="lg" @click="showModal('wPlyr2')">{{ resultForm.wPlyr2 }}</b-button>
      </b-col>
      <br />
    </b-row>
    <br />
    <b-row>
      <br />
      <b-col cols="12" class="d-flex justify-content-center">
        <h3 class="text-danger">敗方</h3>
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-button block variant="outline-danger" size="lg" @click="showModal('lPlyr1')">{{ resultForm.lPlyr1 }}</b-button>
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-form-input type="number" size="lg" class="form-control-danger" v-model="resultForm.lScr" />
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-button block variant="outline-danger" size="lg" @click="showModal('lPlyr2')">{{ resultForm.lPlyr2 }}</b-button>
      </b-col>
      <br />
    </b-row>

    <br /><br />

    <b-row>
      <b-col cols="12" class="d-flex justify-content-center">
        <b-button block variant="outline-primary" size="lg" to="/demo/p1">賽後登錄</b-button>
      </b-col>
    </b-row>

    <!-- The modal -->
    <b-row>
      <b-col cols="12">
        <b-modal ref="my-modal" hide-footer title="選擇選手">
          <b-row>
            <b-col cols="9">
              <b-form-input v-model="searchName" placeholder="姓名" size="lg" />
            </b-col>
            <b-col cols="3">
              <b-button block variant="outline-primary" size="lg">
                <b-icon icon="search" />
              </b-button>
            </b-col>
          </b-row>
          <br />
          <b-row>
            <b-col cols="12">
              <b-list-group v-for="item in items" :key="item.id">
                <b-list-group-item button @click="hideModal(item)">
                  <h5>{{ item.plyrNm }}</h5>
                </b-list-group-item>
              </b-list-group>
            </b-col>
          </b-row>
          <br />
          <b-row>
            <b-col cols="12" class="d-flex justify-content-center">
              <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="my-table" />
            </b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>

    <br /><br />
  </b-container>
</template>

<style>
.form-control-success {
  color: #28a745;
  border-color: #28a745;
}
.form-control-success:focus {
  color: #28a745;
  background-color: #fff;
  border-color: #28a745;
  outline: 0;
  -webkit-box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.5);
  box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.5);
}

.form-control-danger {
  color: #dc3545;
  border-color: #dc3545;
}
.form-control-danger:focus {
  color: #dc3545;
  background-color: #fff;
  border-color: #dc3545;
  outline: 0;
  -webkit-box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.5);
  box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.5);
}
</style>

<script lang="ts" src="./p2.ts"></script>
