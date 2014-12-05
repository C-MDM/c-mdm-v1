/**
 * CodeCategory新規登録アクション。
 */
package jp.co.cos_mos.mdm.v1.service.action;
import jp.co.cos_mos.mdm.core.service.action.SequenceNumberNumberingAction;
import jp.co.cos_mos.mdm.core.service.domain.entity.Control;
import jp.co.cos_mos.mdm.v1.service.domain.CodeCategoryServiceResponse;
import jp.co.cos_mos.mdm.v1.service.domain.entity.CodeCategoryObj;

public interface CodeCategoryCreateAction {

	/**
	 * CodeCategoryを登録します。
	 * 
	 * <ul>
	 * <li>登録データオブジェクトの入力値を検査します。
	 * <li>CodeCategoryの新しいidを取得します。
	 * <li>新しいidを使用し、入力のCodeCategoryをインサートします。
	 * <li>応答オブジェクトに登録済みCodeCategory情報を設定します。
	 * </ul>
	 * 
	 * @param control トランザクション制御情報オブジェクト
	 * @param input 登録データオブジェクト
	 * @return CodeCategoryサービス応答オブジェクト
	 * 
	 * @see SequenceNumberNumberingAction#getEntityNumberingId(String)
	 */
	public abstract CodeCategoryServiceResponse perform(Control control, CodeCategoryObj input);
	
}
