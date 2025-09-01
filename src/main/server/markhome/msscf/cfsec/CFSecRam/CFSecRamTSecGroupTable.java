
// Description: Java 11 in-memory RAM DbIO implementation for TSecGroup.

/*
 *	server.markhome.msscf.CFSec
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfsec.CFSecRam;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;
import server.markhome.msscf.cfsec.CFSecObj.*;
import server.markhome.msscf.cfsec.CFSecObj.*;

/*
 *	CFSecRamTSecGroupTable in-memory RAM DbIO implementation
 *	for TSecGroup.
 */
public class CFSecRamTSecGroupTable
	implements ICFSecTSecGroupTable
{
	private ICFSecSchema schema;
	private Map< CFSecTSecGroupPKey,
				CFSecTSecGroupBuff > dictByPKey
		= new HashMap< CFSecTSecGroupPKey,
				CFSecTSecGroupBuff >();
	private Map< CFSecTSecGroupByTenantIdxKey,
				Map< CFSecTSecGroupPKey,
					CFSecTSecGroupBuff >> dictByTenantIdx
		= new HashMap< CFSecTSecGroupByTenantIdxKey,
				Map< CFSecTSecGroupPKey,
					CFSecTSecGroupBuff >>();
	private Map< CFSecTSecGroupByTenantVisIdxKey,
				Map< CFSecTSecGroupPKey,
					CFSecTSecGroupBuff >> dictByTenantVisIdx
		= new HashMap< CFSecTSecGroupByTenantVisIdxKey,
				Map< CFSecTSecGroupPKey,
					CFSecTSecGroupBuff >>();
	private Map< CFSecTSecGroupByUNameIdxKey,
			CFSecTSecGroupBuff > dictByUNameIdx
		= new HashMap< CFSecTSecGroupByUNameIdxKey,
			CFSecTSecGroupBuff >();

	public CFSecRamTSecGroupTable( ICFSecSchema argSchema ) {
		schema = argSchema;
	}

	public void createTSecGroup( CFSecAuthorization Authorization,
		CFSecTSecGroupBuff Buff )
	{
		final String S_ProcName = "createTSecGroup";
		CFSecTSecGroupPKey pkey = schema.getFactoryTSecGroup().newPKey();
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredTSecGroupId( ((CFSecRamTenantTable)schema.getTableTenant()).nextTSecGroupIdGen( Authorization,
			Buff.getRequiredTenantId() ) );
		Buff.setRequiredTenantId( pkey.getRequiredTenantId() );
		Buff.setRequiredTSecGroupId( pkey.getRequiredTSecGroupId() );
		CFSecTSecGroupByTenantIdxKey keyTenantIdx = schema.getFactoryTSecGroup().newTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( Buff.getRequiredTenantId() );

		CFSecTSecGroupByTenantVisIdxKey keyTenantVisIdx = schema.getFactoryTSecGroup().newTenantVisIdxKey();
		keyTenantVisIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		keyTenantVisIdx.setRequiredIsVisible( Buff.getRequiredIsVisible() );

		CFSecTSecGroupByUNameIdxKey keyUNameIdx = schema.getFactoryTSecGroup().newUNameIdxKey();
		keyUNameIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		keyUNameIdx.setRequiredName( Buff.getRequiredName() );

		// Validate unique indexes

		if( dictByPKey.containsKey( pkey ) ) {
			throw new CFLibPrimaryKeyNotNewException( getClass(), S_ProcName, pkey );
		}

		if( dictByUNameIdx.containsKey( keyUNameIdx ) ) {
			throw new CFLibUniqueIndexViolationException( getClass(),
				S_ProcName,
				"TSecGroupUNameIdx",
				keyUNameIdx );
		}

		// Validate foreign keys

		{
			boolean allNull = true;
			allNull = false;
			if( ! allNull ) {
				if( null == schema.getTableTenant().readDerivedByIdIdx( Authorization,
						Buff.getRequiredTenantId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						S_ProcName,
						"Container",
						"TSecGroupTenant",
						"Tenant",
						null );
				}
			}
		}

		// Proceed with adding the new record

		dictByPKey.put( pkey, Buff );

		Map< CFSecTSecGroupPKey, CFSecTSecGroupBuff > subdictTenantIdx;
		if( dictByTenantIdx.containsKey( keyTenantIdx ) ) {
			subdictTenantIdx = dictByTenantIdx.get( keyTenantIdx );
		}
		else {
			subdictTenantIdx = new HashMap< CFSecTSecGroupPKey, CFSecTSecGroupBuff >();
			dictByTenantIdx.put( keyTenantIdx, subdictTenantIdx );
		}
		subdictTenantIdx.put( pkey, Buff );

		Map< CFSecTSecGroupPKey, CFSecTSecGroupBuff > subdictTenantVisIdx;
		if( dictByTenantVisIdx.containsKey( keyTenantVisIdx ) ) {
			subdictTenantVisIdx = dictByTenantVisIdx.get( keyTenantVisIdx );
		}
		else {
			subdictTenantVisIdx = new HashMap< CFSecTSecGroupPKey, CFSecTSecGroupBuff >();
			dictByTenantVisIdx.put( keyTenantVisIdx, subdictTenantVisIdx );
		}
		subdictTenantVisIdx.put( pkey, Buff );

		dictByUNameIdx.put( keyUNameIdx, Buff );

	}

	public CFSecTSecGroupBuff readDerived( CFSecAuthorization Authorization,
		CFSecTSecGroupPKey PKey )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readDerived";
		CFSecTSecGroupPKey key = schema.getFactoryTSecGroup().newPKey();
		key.setRequiredTenantId( PKey.getRequiredTenantId() );
		key.setRequiredTSecGroupId( PKey.getRequiredTSecGroupId() );
		CFSecTSecGroupBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGroupBuff lockDerived( CFSecAuthorization Authorization,
		CFSecTSecGroupPKey PKey )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readDerived";
		CFSecTSecGroupPKey key = schema.getFactoryTSecGroup().newPKey();
		key.setRequiredTenantId( PKey.getRequiredTenantId() );
		key.setRequiredTSecGroupId( PKey.getRequiredTSecGroupId() );
		CFSecTSecGroupBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGroupBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "CFSecRamTSecGroup.readAllDerived";
		CFSecTSecGroupBuff[] retList = new CFSecTSecGroupBuff[ dictByPKey.values().size() ];
		Iterator< CFSecTSecGroupBuff > iter = dictByPKey.values().iterator();
		int idx = 0;
		while( iter.hasNext() ) {
			retList[ idx++ ] = iter.next();
		}
		return( retList );
	}

	public CFSecTSecGroupBuff[] readDerivedByTenantIdx( CFSecAuthorization Authorization,
		long TenantId )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readDerivedByTenantIdx";
		CFSecTSecGroupByTenantIdxKey key = schema.getFactoryTSecGroup().newTenantIdxKey();
		key.setRequiredTenantId( TenantId );

		CFSecTSecGroupBuff[] recArray;
		if( dictByTenantIdx.containsKey( key ) ) {
			Map< CFSecTSecGroupPKey, CFSecTSecGroupBuff > subdictTenantIdx
				= dictByTenantIdx.get( key );
			recArray = new CFSecTSecGroupBuff[ subdictTenantIdx.size() ];
			Iterator< CFSecTSecGroupBuff > iter = subdictTenantIdx.values().iterator();
			int idx = 0;
			while( iter.hasNext() ) {
				recArray[ idx++ ] = iter.next();
			}
		}
		else {
			Map< CFSecTSecGroupPKey, CFSecTSecGroupBuff > subdictTenantIdx
				= new HashMap< CFSecTSecGroupPKey, CFSecTSecGroupBuff >();
			dictByTenantIdx.put( key, subdictTenantIdx );
			recArray = new CFSecTSecGroupBuff[0];
		}
		return( recArray );
	}

	public CFSecTSecGroupBuff[] readDerivedByTenantVisIdx( CFSecAuthorization Authorization,
		long TenantId,
		boolean IsVisible )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readDerivedByTenantVisIdx";
		CFSecTSecGroupByTenantVisIdxKey key = schema.getFactoryTSecGroup().newTenantVisIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredIsVisible( IsVisible );

		CFSecTSecGroupBuff[] recArray;
		if( dictByTenantVisIdx.containsKey( key ) ) {
			Map< CFSecTSecGroupPKey, CFSecTSecGroupBuff > subdictTenantVisIdx
				= dictByTenantVisIdx.get( key );
			recArray = new CFSecTSecGroupBuff[ subdictTenantVisIdx.size() ];
			Iterator< CFSecTSecGroupBuff > iter = subdictTenantVisIdx.values().iterator();
			int idx = 0;
			while( iter.hasNext() ) {
				recArray[ idx++ ] = iter.next();
			}
		}
		else {
			Map< CFSecTSecGroupPKey, CFSecTSecGroupBuff > subdictTenantVisIdx
				= new HashMap< CFSecTSecGroupPKey, CFSecTSecGroupBuff >();
			dictByTenantVisIdx.put( key, subdictTenantVisIdx );
			recArray = new CFSecTSecGroupBuff[0];
		}
		return( recArray );
	}

	public CFSecTSecGroupBuff readDerivedByUNameIdx( CFSecAuthorization Authorization,
		long TenantId,
		String Name )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readDerivedByUNameIdx";
		CFSecTSecGroupByUNameIdxKey key = schema.getFactoryTSecGroup().newUNameIdxKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredName( Name );

		CFSecTSecGroupBuff buff;
		if( dictByUNameIdx.containsKey( key ) ) {
			buff = dictByUNameIdx.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGroupBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readDerivedByIdIdx() ";
		CFSecTSecGroupPKey key = schema.getFactoryTSecGroup().newPKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredTSecGroupId( TSecGroupId );

		CFSecTSecGroupBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGroupBuff readBuff( CFSecAuthorization Authorization,
		CFSecTSecGroupPKey PKey )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readBuff";
		CFSecTSecGroupBuff buff = readDerived( Authorization, PKey );
		if( ( buff != null ) && ( ! buff.getClassCode().equals( "a016" ) ) ) {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGroupBuff lockBuff( CFSecAuthorization Authorization,
		CFSecTSecGroupPKey PKey )
	{
		final String S_ProcName = "lockBuff";
		CFSecTSecGroupBuff buff = readDerived( Authorization, PKey );
		if( ( buff != null ) && ( ! buff.getClassCode().equals( "a016" ) ) ) {
			buff = null;
		}
		return( buff );
	}

	public CFSecTSecGroupBuff[] readAllBuff( CFSecAuthorization Authorization )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readAllBuff";
		CFSecTSecGroupBuff buff;
		ArrayList<CFSecTSecGroupBuff> filteredList = new ArrayList<CFSecTSecGroupBuff>();
		CFSecTSecGroupBuff[] buffList = readAllDerived( Authorization );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a016" ) ) {
				filteredList.add( buff );
			}
		}
		return( filteredList.toArray( new CFSecTSecGroupBuff[0] ) );
	}

	public CFSecTSecGroupBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readBuffByIdIdx() ";
		CFSecTSecGroupBuff buff = readDerivedByIdIdx( Authorization,
			TenantId,
			TSecGroupId );
		if( ( buff != null ) && buff.getClassCode().equals( "a016" ) ) {
			return( (CFSecTSecGroupBuff)buff );
		}
		else {
			return( null );
		}
	}

	public CFSecTSecGroupBuff[] readBuffByTenantIdx( CFSecAuthorization Authorization,
		long TenantId )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readBuffByTenantIdx() ";
		CFSecTSecGroupBuff buff;
		ArrayList<CFSecTSecGroupBuff> filteredList = new ArrayList<CFSecTSecGroupBuff>();
		CFSecTSecGroupBuff[] buffList = readDerivedByTenantIdx( Authorization,
			TenantId );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a016" ) ) {
				filteredList.add( (CFSecTSecGroupBuff)buff );
			}
		}
		return( filteredList.toArray( new CFSecTSecGroupBuff[0] ) );
	}

	public CFSecTSecGroupBuff[] readBuffByTenantVisIdx( CFSecAuthorization Authorization,
		long TenantId,
		boolean IsVisible )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readBuffByTenantVisIdx() ";
		CFSecTSecGroupBuff buff;
		ArrayList<CFSecTSecGroupBuff> filteredList = new ArrayList<CFSecTSecGroupBuff>();
		CFSecTSecGroupBuff[] buffList = readDerivedByTenantVisIdx( Authorization,
			TenantId,
			IsVisible );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a016" ) ) {
				filteredList.add( (CFSecTSecGroupBuff)buff );
			}
		}
		return( filteredList.toArray( new CFSecTSecGroupBuff[0] ) );
	}

	public CFSecTSecGroupBuff readBuffByUNameIdx( CFSecAuthorization Authorization,
		long TenantId,
		String Name )
	{
		final String S_ProcName = "CFSecRamTSecGroup.readBuffByUNameIdx() ";
		CFSecTSecGroupBuff buff = readDerivedByUNameIdx( Authorization,
			TenantId,
			Name );
		if( ( buff != null ) && buff.getClassCode().equals( "a016" ) ) {
			return( (CFSecTSecGroupBuff)buff );
		}
		else {
			return( null );
		}
	}

	public void updateTSecGroup( CFSecAuthorization Authorization,
		CFSecTSecGroupBuff Buff )
	{
		CFSecTSecGroupPKey pkey = schema.getFactoryTSecGroup().newPKey();
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredTSecGroupId( Buff.getRequiredTSecGroupId() );
		CFSecTSecGroupBuff existing = dictByPKey.get( pkey );
		if( existing == null ) {
			throw new CFLibStaleCacheDetectedException( getClass(),
				"updateTSecGroup",
				"Existing record not found",
				"TSecGroup",
				pkey );
		}
		if( existing.getRequiredRevision() != Buff.getRequiredRevision() ) {
			throw new CFLibCollisionDetectedException( getClass(),
				"updateTSecGroup",
				pkey );
		}
		Buff.setRequiredRevision( Buff.getRequiredRevision() + 1 );
		CFSecTSecGroupByTenantIdxKey existingKeyTenantIdx = schema.getFactoryTSecGroup().newTenantIdxKey();
		existingKeyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		CFSecTSecGroupByTenantIdxKey newKeyTenantIdx = schema.getFactoryTSecGroup().newTenantIdxKey();
		newKeyTenantIdx.setRequiredTenantId( Buff.getRequiredTenantId() );

		CFSecTSecGroupByTenantVisIdxKey existingKeyTenantVisIdx = schema.getFactoryTSecGroup().newTenantVisIdxKey();
		existingKeyTenantVisIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		existingKeyTenantVisIdx.setRequiredIsVisible( existing.getRequiredIsVisible() );

		CFSecTSecGroupByTenantVisIdxKey newKeyTenantVisIdx = schema.getFactoryTSecGroup().newTenantVisIdxKey();
		newKeyTenantVisIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		newKeyTenantVisIdx.setRequiredIsVisible( Buff.getRequiredIsVisible() );

		CFSecTSecGroupByUNameIdxKey existingKeyUNameIdx = schema.getFactoryTSecGroup().newUNameIdxKey();
		existingKeyUNameIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		existingKeyUNameIdx.setRequiredName( existing.getRequiredName() );

		CFSecTSecGroupByUNameIdxKey newKeyUNameIdx = schema.getFactoryTSecGroup().newUNameIdxKey();
		newKeyUNameIdx.setRequiredTenantId( Buff.getRequiredTenantId() );
		newKeyUNameIdx.setRequiredName( Buff.getRequiredName() );

		// Check unique indexes

		if( ! existingKeyUNameIdx.equals( newKeyUNameIdx ) ) {
			if( dictByUNameIdx.containsKey( newKeyUNameIdx ) ) {
				throw new CFLibUniqueIndexViolationException( getClass(),
					"updateTSecGroup",
					"TSecGroupUNameIdx",
					newKeyUNameIdx );
			}
		}

		// Validate foreign keys

		{
			boolean allNull = true;

			if( allNull ) {
				if( null == schema.getTableTenant().readDerivedByIdIdx( Authorization,
						Buff.getRequiredTenantId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						"updateTSecGroup",
						"Container",
						"TSecGroupTenant",
						"Tenant",
						null );
				}
			}
		}

		// Update is valid

		Map< CFSecTSecGroupPKey, CFSecTSecGroupBuff > subdict;

		dictByPKey.remove( pkey );
		dictByPKey.put( pkey, Buff );

		subdict = dictByTenantIdx.get( existingKeyTenantIdx );
		if( subdict != null ) {
			subdict.remove( pkey );
		}
		if( dictByTenantIdx.containsKey( newKeyTenantIdx ) ) {
			subdict = dictByTenantIdx.get( newKeyTenantIdx );
		}
		else {
			subdict = new HashMap< CFSecTSecGroupPKey, CFSecTSecGroupBuff >();
			dictByTenantIdx.put( newKeyTenantIdx, subdict );
		}
		subdict.put( pkey, Buff );

		subdict = dictByTenantVisIdx.get( existingKeyTenantVisIdx );
		if( subdict != null ) {
			subdict.remove( pkey );
		}
		if( dictByTenantVisIdx.containsKey( newKeyTenantVisIdx ) ) {
			subdict = dictByTenantVisIdx.get( newKeyTenantVisIdx );
		}
		else {
			subdict = new HashMap< CFSecTSecGroupPKey, CFSecTSecGroupBuff >();
			dictByTenantVisIdx.put( newKeyTenantVisIdx, subdict );
		}
		subdict.put( pkey, Buff );

		dictByUNameIdx.remove( existingKeyUNameIdx );
		dictByUNameIdx.put( newKeyUNameIdx, Buff );

	}

	public void deleteTSecGroup( CFSecAuthorization Authorization,
		CFSecTSecGroupBuff Buff )
	{
		final String S_ProcName = "CFSecRamTSecGroupTable.deleteTSecGroup() ";
		String classCode;
		CFSecTSecGroupPKey pkey = schema.getFactoryTSecGroup().newPKey();
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredTSecGroupId( Buff.getRequiredTSecGroupId() );
		CFSecTSecGroupBuff existing = dictByPKey.get( pkey );
		if( existing == null ) {
			return;
		}
		if( existing.getRequiredRevision() != Buff.getRequiredRevision() )
		{
			throw new CFLibCollisionDetectedException( getClass(),
				"deleteTSecGroup",
				pkey );
		}
					schema.getTableTSecGrpInc().deleteTSecGrpIncByIncludeIdx( Authorization,
						existing.getRequiredTenantId(),
						existing.getRequiredTSecGroupId() );
					schema.getTableTSecGrpMemb().deleteTSecGrpMembByGroupIdx( Authorization,
						existing.getRequiredTenantId(),
						existing.getRequiredTSecGroupId() );
					schema.getTableTSecGrpInc().deleteTSecGrpIncByGroupIdx( Authorization,
						existing.getRequiredTenantId(),
						existing.getRequiredTSecGroupId() );
		CFSecTSecGroupByTenantIdxKey keyTenantIdx = schema.getFactoryTSecGroup().newTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		CFSecTSecGroupByTenantVisIdxKey keyTenantVisIdx = schema.getFactoryTSecGroup().newTenantVisIdxKey();
		keyTenantVisIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyTenantVisIdx.setRequiredIsVisible( existing.getRequiredIsVisible() );

		CFSecTSecGroupByUNameIdxKey keyUNameIdx = schema.getFactoryTSecGroup().newUNameIdxKey();
		keyUNameIdx.setRequiredTenantId( existing.getRequiredTenantId() );
		keyUNameIdx.setRequiredName( existing.getRequiredName() );

		// Validate reverse foreign keys

		// Delete is valid
		Map< CFSecTSecGroupPKey, CFSecTSecGroupBuff > subdict;

		dictByPKey.remove( pkey );

		subdict = dictByTenantIdx.get( keyTenantIdx );
		subdict.remove( pkey );

		subdict = dictByTenantVisIdx.get( keyTenantVisIdx );
		subdict.remove( pkey );

		dictByUNameIdx.remove( keyUNameIdx );

	}
	public void deleteTSecGroupByIdIdx( CFSecAuthorization Authorization,
		long argTenantId,
		int argTSecGroupId )
	{
		CFSecTSecGroupPKey key = schema.getFactoryTSecGroup().newPKey();
		key.setRequiredTenantId( argTenantId );
		key.setRequiredTSecGroupId( argTSecGroupId );
		deleteTSecGroupByIdIdx( Authorization, key );
	}

	public void deleteTSecGroupByIdIdx( CFSecAuthorization Authorization,
		CFSecTSecGroupPKey argKey )
	{
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		CFSecTSecGroupBuff cur;
		LinkedList<CFSecTSecGroupBuff> matchSet = new LinkedList<CFSecTSecGroupBuff>();
		Iterator<CFSecTSecGroupBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGroupBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGroup().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGroupId() );
			deleteTSecGroup( Authorization, cur );
		}
	}

	public void deleteTSecGroupByTenantIdx( CFSecAuthorization Authorization,
		long argTenantId )
	{
		CFSecTSecGroupByTenantIdxKey key = schema.getFactoryTSecGroup().newTenantIdxKey();
		key.setRequiredTenantId( argTenantId );
		deleteTSecGroupByTenantIdx( Authorization, key );
	}

	public void deleteTSecGroupByTenantIdx( CFSecAuthorization Authorization,
		CFSecTSecGroupByTenantIdxKey argKey )
	{
		CFSecTSecGroupBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTSecGroupBuff> matchSet = new LinkedList<CFSecTSecGroupBuff>();
		Iterator<CFSecTSecGroupBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGroupBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGroup().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGroupId() );
			deleteTSecGroup( Authorization, cur );
		}
	}

	public void deleteTSecGroupByTenantVisIdx( CFSecAuthorization Authorization,
		long argTenantId,
		boolean argIsVisible )
	{
		CFSecTSecGroupByTenantVisIdxKey key = schema.getFactoryTSecGroup().newTenantVisIdxKey();
		key.setRequiredTenantId( argTenantId );
		key.setRequiredIsVisible( argIsVisible );
		deleteTSecGroupByTenantVisIdx( Authorization, key );
	}

	public void deleteTSecGroupByTenantVisIdx( CFSecAuthorization Authorization,
		CFSecTSecGroupByTenantVisIdxKey argKey )
	{
		CFSecTSecGroupBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTSecGroupBuff> matchSet = new LinkedList<CFSecTSecGroupBuff>();
		Iterator<CFSecTSecGroupBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGroupBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGroup().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGroupId() );
			deleteTSecGroup( Authorization, cur );
		}
	}

	public void deleteTSecGroupByUNameIdx( CFSecAuthorization Authorization,
		long argTenantId,
		String argName )
	{
		CFSecTSecGroupByUNameIdxKey key = schema.getFactoryTSecGroup().newUNameIdxKey();
		key.setRequiredTenantId( argTenantId );
		key.setRequiredName( argName );
		deleteTSecGroupByUNameIdx( Authorization, key );
	}

	public void deleteTSecGroupByUNameIdx( CFSecAuthorization Authorization,
		CFSecTSecGroupByUNameIdxKey argKey )
	{
		CFSecTSecGroupBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTSecGroupBuff> matchSet = new LinkedList<CFSecTSecGroupBuff>();
		Iterator<CFSecTSecGroupBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTSecGroupBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTSecGroup().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredTSecGroupId() );
			deleteTSecGroup( Authorization, cur );
		}
	}

	public void releasePreparedStatements() {
	}
}
