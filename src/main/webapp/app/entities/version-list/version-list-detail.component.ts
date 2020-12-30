import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVersionList } from 'app/shared/model/version-list.model';

@Component({
  selector: 'jhi-version-list-detail',
  templateUrl: './version-list-detail.component.html',
})
export class VersionListDetailComponent implements OnInit {
  versionList: IVersionList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ versionList }) => (this.versionList = versionList));
  }

  previousState(): void {
    window.history.back();
  }
}
