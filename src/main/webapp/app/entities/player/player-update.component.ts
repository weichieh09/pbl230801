import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { IPlayer, Player } from '@/shared/model/player.model';
import PlayerService from './player.service';

const validations: any = {
  player: {
    plyrNm: {},
    plyrLvl: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class PlayerUpdate extends Vue {
  @Inject('playerService') private playerService: () => PlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public player: IPlayer = new Player();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerId) {
        vm.retrievePlayer(to.params.playerId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.player.id) {
      this.playerService()
        .update(this.player)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Player is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.playerService()
        .create(this.player)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Player is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.player[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.player[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.player[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.player[field] = null;
    }
  }

  public retrievePlayer(playerId): void {
    this.playerService()
      .find(playerId)
      .then(res => {
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.player = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
