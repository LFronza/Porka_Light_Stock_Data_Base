import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApresentacao } from 'app/shared/model/apresentacao.model';
import { ApresentacaoService } from './apresentacao.service';

@Component({
    selector: 'jhi-apresentacao-delete-dialog',
    templateUrl: './apresentacao-delete-dialog.component.html'
})
export class ApresentacaoDeleteDialogComponent {
    apresentacao: IApresentacao;

    constructor(
        protected apresentacaoService: ApresentacaoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apresentacaoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apresentacaoListModification',
                content: 'Deleted an apresentacao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-apresentacao-delete-popup',
    template: ''
})
export class ApresentacaoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apresentacao }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApresentacaoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.apresentacao = apresentacao;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/apresentacao', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/apresentacao', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
