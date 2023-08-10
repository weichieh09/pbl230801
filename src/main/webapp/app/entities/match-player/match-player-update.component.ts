import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { IMatchPlayer, MatchPlayer } from '@/shared/model/match-player.model';
import MatchPlayerService from './match-player.service';

const validations: any = {
  matchPlayer: {
    mId: {},
    pId: {},
    eId: {},
    mtchEndTime: {},
    score: {},
    winFg: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class MatchPlayerUpdate extends Vue {
  @Inject('matchPlayerService') private matchPlayerService: () => MatchPlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public matchPlayer: IMatchPlayer = new MatchPlayer();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchPlayerId) {
        vm.retrieveMatchPlayer(to.params.matchPlayerId);
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
    if (this.matchPlayer.id) {
      this.matchPlayerService()
        .update(this.matchPlayer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A MatchPlayer is updated with identifier ' + param.id;
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
      this.matchPlayerService()
        .create(this.matchPlayer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A MatchPlayer is created with identifier ' + param.id;
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
      this.matchPlayer[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.matchPlayer[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.matchPlayer[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.matchPlayer[field] = null;
    }
  }

  public retrieveMatchPlayer(matchPlayerId): void {
    this.matchPlayerService()
      .find(matchPlayerId)
      .then(res => {
        res.mtchEndTime = new Date(res.mtchEndTime);
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.matchPlayer = res;
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
