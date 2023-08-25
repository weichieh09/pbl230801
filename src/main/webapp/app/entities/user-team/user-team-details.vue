<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="userTeam">
        <h2 class="jh-entity-heading" data-cy="userTeamDetailsHeading"><span>UserTeam</span> {{ userTeam.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>U Id</span>
          </dt>
          <dd>
            <span>{{ userTeam.uId }}</span>
          </dd>
          <dt>
            <span>T Id</span>
          </dt>
          <dd>
            <span>{{ userTeam.tId }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Usr</span>
          </dt>
          <dd>
            <span>{{ userTeam.lstMtnUsr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Dt</span>
          </dt>
          <dd>
            <span>{{ userTeam.lstMtnDt | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="userTeam.id" :to="{ name: 'UserTeamEdit', params: { userTeamId: userTeam.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-team-details.component.ts"></script>
