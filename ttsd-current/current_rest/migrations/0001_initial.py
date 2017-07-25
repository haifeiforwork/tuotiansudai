# -*- coding: utf-8 -*-
# Generated by Django 1.10.1 on 2017-07-25 15:10
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Agent',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('login_name', models.CharField(default=None, max_length=60)),
                ('mobile', models.CharField(default=None, max_length=20)),
                ('active', models.BooleanField(default=False)),
            ],
            options={
                'db_table': 'agent',
            },
        ),
        migrations.CreateModel(
            name='CurrentAccount',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('login_name', models.CharField(max_length=25, unique=True)),
                ('balance', models.PositiveIntegerField(default=0)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
            ],
            options={
                'db_table': 'current_account',
            },
        ),
        migrations.CreateModel(
            name='CurrentBill',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('login_name', models.CharField(max_length=25)),
                ('bill_date', models.DateTimeField()),
                ('bill_type', models.CharField(choices=[(b'DEPOSIT', '\u4e70\u5165'), (b'WITHDRAW', '\u8d4e\u56de'), (b'INTEREST', '\u7ed3\u606f')], max_length=10)),
                ('amount', models.PositiveIntegerField()),
                ('balance', models.PositiveIntegerField()),
                ('order_id', models.IntegerField()),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('current_account', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='current_bills', related_query_name='current_bill', to='current_rest.CurrentAccount')),
            ],
            options={
                'db_table': 'current_bill',
            },
        ),
        migrations.CreateModel(
            name='CurrentDeposit',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('login_name', models.CharField(max_length=25)),
                ('amount', models.PositiveIntegerField()),
                ('status', models.CharField(choices=[(b'WAITING_PAY', '\u5f85\u652f\u4ed8'), (b'SUCCESS', '\u4e70\u5165\u6210\u529f'), (b'FAIL', '\u4e70\u5165\u5931\u8d25')], default=b'WAITING_PAY', max_length=20)),
                ('source', models.CharField(choices=[(b'WEB', '\u7f51\u7ad9'), (b'WE_CHAT', '\u5fae\u4fe1'), (b'IOS', 'iOS'), (b'ANDROID', 'Android')], default=b'WEB', max_length=10)),
                ('no_password', models.BooleanField(default=False)),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('current_account', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='current_deposits', related_query_name='current_deposit', to='current_rest.CurrentAccount')),
            ],
            options={
                'db_table': 'current_deposit',
            },
        ),
        migrations.CreateModel(
            name='Loan',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_time', models.DateTimeField(auto_now_add=True)),
                ('updated_time', models.DateTimeField(auto_now=True)),
                ('serial_number', models.IntegerField()),
                ('amount', models.FloatField(null=True)),
                ('loan_type', models.CharField(choices=[(b'HOUSE', '\u623f\u4ea7\u62b5\u62bc\u501f\u6b3e'), (b'VEHICLE', '\u8f66\u8f86\u62b5\u62bc\u501f\u6b3e'), (b'FACTORING', '\u4f01\u4e1a\u7ecf\u8425\u6027\u501f\u6b3e-\u4fdd\u7406'), (b'BILL', '\u4f01\u4e1a\u7ecf\u8425\u6027\u501f\u6b3e-\u7968\u636e'), (b'SHUIYI', '\u7a0e\u6613\u7ecf\u8425\u6027\u501f\u6b3e'), (b'HUIZU', '\u6c7d\u8f66\u878d\u8d44\u79df\u8d41')], max_length=40)),
                ('debtor', models.CharField(max_length=60)),
                ('debtor_identity_card', models.CharField(max_length=18)),
                ('effective_date', models.DateTimeField()),
                ('expiration_date', models.DateTimeField()),
                ('creator', models.CharField(max_length=25)),
                ('auditor', models.CharField(max_length=25)),
                ('status', models.CharField(choices=[(b'APPROVING', '\u5f85\u5ba1\u6838'), (b'APPROVED', '\u5df2\u5ba1\u6838'), (b'EXPIRED', '\u5df2\u8fc7\u671f')], max_length=20)),
                ('agent', models.ForeignKey(null=True, on_delete=django.db.models.deletion.PROTECT, related_name='+', to='current_rest.Agent')),
            ],
            options={
                'db_table': 'loan',
            },
        ),
        migrations.CreateModel(
            name='OperationLog',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('refer_type', models.CharField(choices=[(b'loan', '\u503a\u6743')], max_length=50)),
                ('refer_pk', models.IntegerField()),
                ('operator', models.CharField(max_length=50)),
                ('operation_type', models.CharField(choices=[(b'LOAN_ADD', '\u589e\u52a0\u503a\u6743\u4fe1\u606f'), (b'LOAN_AUDIT', '\u5ba1\u6838\u503a\u6743\u4fe1\u606f')], max_length=100, null=True)),
                ('timestamp', models.DateTimeField(auto_now_add=True)),
                ('content', models.TextField()),
            ],
            options={
                'db_table': 'operation_log',
            },
        ),
    ]
