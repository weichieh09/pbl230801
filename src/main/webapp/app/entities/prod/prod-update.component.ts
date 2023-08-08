import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IProd, Prod } from '@/shared/model/prod.model';
import ProdService from './prod.service';

const validations: any = {
  prod: {
    prodNo: {
      maxLength: maxLength(30),
    },
    chName: {
      maxLength: maxLength(100),
    },
    issuDt: {},
    expDt: {},
  },
};

@Component({
  validations,
})
export default class ProdUpdate extends Vue {
  @Inject('prodService') private prodService: () => ProdService;
  @Inject('alertService') private alertService: () => AlertService;

  public prod: IProd = new Prod();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.prodId) {
        vm.retrieveProd(to.params.prodId);
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
    if (this.prod.id) {
      this.prodService()
        .update(this.prod)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Prod is updated with identifier ' + param.id;
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
      this.prodService()
        .create(this.prod)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Prod is created with identifier ' + param.id;
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

  public retrieveProd(prodId): void {
    this.prodService()
      .find(prodId)
      .then(res => {
        this.prod = res;
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
